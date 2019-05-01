package ru.ancevt.maed.world;

import java.util.ArrayList;
import java.util.List;

import ru.ancevt.d2d2.common.BorderedRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObject;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.texture.TextureManager;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.common.PlayProcessor;
import ru.ancevt.maed.gameobject.Actor;
import ru.ancevt.maed.gameobject.Ava;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.IResettable;
import ru.ancevt.maed.gameobject.Scenery;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.gameobject.area.Area;
import ru.ancevt.maed.gameobject.area.AreaCheckpoint;
import ru.ancevt.maed.gameobject.weapon.Bullet;
import ru.ancevt.maed.gameobject.weapon.Weapon;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.Room;

public class World extends DisplayObjectContainer implements IWorld {

	private static World instance;
	public static final World getWorld() {
		if(instance == null) throw new RuntimeException("World does not exist");
		return instance;
	}
	
	private final PlayProcessor playProcessor;
	private final Camera camera;
	private final List<IGameObject> gameObjects;
	
	private boolean playMode;
	private boolean sceneryPacked;
	
	private Map map;
	private int roomId;
	
	private final WorldLayer[] layers;
	
	private UserActor userActor;
	private BorderedRect roomRect;

	private PackedScenery packedSceneryBack;
	private PackedScenery packedSceneryFore;
	
	private Overlay overlay;
	private boolean switchingRoomsNow;
	
	private boolean areasVisible;
	
	private WorldListener listener;
	
	public World() {
		if (instance != null) 
			throw new RuntimeException("Can not create multiple World instance");
		
		instance = this;
		
		gameObjects = new ArrayList<IGameObject>();
		
		layers = new WorldLayer[WorldLayer.LAYER_COUNT];
		
		for(int i = 0; i < layers.length; i ++) {
			final WorldLayer layer = new WorldLayer(i);
			layers[i] = layer;
			add(layer);
		}
		
		playProcessor = new PlayProcessor();
		playProcessor.setWorld(this);
		
		camera = new Camera(this);
		
		areasVisible = true;
		
		createUserActor();
	}

	public final boolean isSceneryPacked() {
        return sceneryPacked;
    }

    public final void setSceneryPacked(final boolean sceneryPacked) {
        if(this.sceneryPacked == sceneryPacked) return;
    	
    	this.sceneryPacked = sceneryPacked;

        final int TARGET_LAYER_INDEX_BG = 0;
        final int TARGET_LAYER_INDEX_FG = 8;

        removeOldPackedScenery(packedSceneryBack);
        removeOldPackedScenery(packedSceneryFore);
        
        if(sceneryPacked) {
        	final Room room = map.getRoomById(roomId);

        	packedSceneryBack = SceneryPacker.pack(room, 0, 4);
            getLayer(TARGET_LAYER_INDEX_BG).add(packedSceneryBack);

            packedSceneryFore = SceneryPacker.pack(room, 7, 8);
            getLayer(TARGET_LAYER_INDEX_FG).add(packedSceneryFore);

            removeSceneries();
        } else {
        	setRoomId(roomId);
        }
    }
    
    private final void removeOldPackedScenery(final PackedScenery ps) {
        if(ps == null) return;
        if(ps.hasParent()) ps.getParent().remove(ps);
        TextureManager.getInstance().unloadTextureAtlas(ps.getTexture().getTextureAtlas());
        System.gc();
    }
	
	private final void removeSceneries() {
        final List<IGameObject> toRemove = new ArrayList<IGameObject>();

        for(int i = 0; i < gameObjects.size(); i ++) {
            final IGameObject o = gameObjects.get(i);
            if(o instanceof Scenery) {
                final Scenery s = (Scenery)o;
                if(s.hasParent()) s.getParent().remove(s);
                toRemove.add(o);
            }
        }

        gameObjects.removeAll(toRemove);
    }

	
	public void setRoomRectVisible(boolean value) {
		if(value == isRoomRectVisible()) return;
		
		if(value) {
			roomRect = new BorderedRect(null, Color.DARK_BLUE);
			
			if(map != null) {
				final Room room = map.getRoomById(roomId);
				final int roomWidth = room.getWidth();
				final int roomHeight = room.getHeight();
				
				roomRect = new BorderedRect(
					roomWidth, 
					roomHeight, 
					null, 
					Color.DARK_BLUE
				);
				add(roomRect);
			}
		} else {
			if(roomRect.hasParent()) remove(roomRect);
			roomRect = null;
		}
	}
	
	public boolean isRoomRectVisible() {
		return roomRect != null;
	}
	
	public void setAreasVisible(boolean value) {
		this.areasVisible = value;
		
		for(int i = 0; i < getGameObjectsCount(); i ++) {
			final IGameObject o = getGameObject(i);
			if(o instanceof Area) {
				final Area a = (Area) o;
				a.setVisible(value);
			}
		}
	}
	
	public boolean isAreasVisible() {
		return areasVisible;
	}
	
	@Override
	public void onEachFrame() {
		if(playMode) {
			playProcessor.process();
			camera.process();
		}
	}

	@Override
	public int getGameObjectsCount() {
		return gameObjects.size();
	}

	@Override
	public IGameObject getGameObject(int index) {
		return gameObjects.get(index);
	}

	@Override
	public boolean isSwitchingRoomsNow() {
		return switchingRoomsNow;
	}
	
	public void resetAllResettables() {
		for(int i = 0; i < gameObjects.size(); i ++) {
			final IGameObject gameObject = gameObjects.get(i);
			if(gameObject instanceof IResettable) {
				final IResettable r = (IResettable) gameObject;
				if(r != userActor) r.reset();
			}
		}
	}

	@Override
	public void addGameObject(IGameObject gameObject, int layer, boolean updateRoom) {
		gameObjects.add(gameObject);
		getLayer(layer).add((DisplayObject)gameObject);
		
		if(updateRoom) map.getRoomById(roomId).addGameObject(gameObject, layer);
	}

	@Override
	public void removeGameObject(IGameObject gameObject, boolean updateRoom) {
		gameObjects.remove(gameObject);
		for(int i = 0; i < layers.length; i ++) {
			final WorldLayer layer = layers[i];
			if(layer == gameObject.getParent()) {
				layer.remove((DisplayObject)gameObject);
				
				if(updateRoom) map.getRoomById(roomId).removeGameObject(gameObject, layer.getIndex());
			}
		}
	}

	@Override
	public Map getMap() {
		return map;
	}

	@Override
	public void setMap(Map map) {
		clear();
		this.map = map;
		
		playProcessor.setGravity(map.getGravity());
		
		if(map.getRoomsCount() > 0) 
			setRoomId(map.getRoomByIndex(0).getId());
	}
	
	public void update() {
		final Room room = map.getRoomById(roomId);
		getStage().setBackgroundColor(room.getBackColor());
		playProcessor.setGravity(map.getGravity());
		getCamera().setBounds(room.getWidth(), room.getHeight());
	}
	
	public void setRoomId(int roomId) {
		setSceneryPacked(false);
		clear();
		
		this.roomId = roomId;
		final Room room = map.getRoomById(roomId);
		
		for(int layer = 0; layer < WorldLayer.LAYER_COUNT; layer ++) {
			final int objectCount = room.getGameObjectsCount(layer);
			for(int index = 0; index < objectCount; index ++) {
				final IGameObject gameObject = room.getGameObject(layer, index);
				
				if(!areasVisible && gameObject instanceof Area) {
					((Area)gameObject).setVisible(false);
				}
				
				addGameObject(gameObject, layer, false);
			}
		}
		
		camera.setBounds(room.getWidth(), room.getHeight());
		
		if(isRoomRectVisible()) {
			setRoomRectVisible(false);
			setRoomRectVisible(true);
		}
		
		update();
		
		if(listener != null) {
			listener.onWorldRoomChange(room);
		}
	}
	
	public void switchRoom(int roomIdSwitchTo, float actorX, float actorY) {
		if(isSwitchingRoomsNow()) return;
		
		final Room oldRoom = map.getRoomById(this.roomId);
		
		overlay = new Overlay(oldRoom.getWidth(), oldRoom.getHeight()) {
			@Override
			public void onStateChanged(int state) {
				if (state == Overlay.STATE_BLACK) {
					final Room newRoom = map.getRoomById(roomIdSwitchTo);
					setRoomId(roomIdSwitchTo);
					setSize(newRoom.getWidth(), newRoom.getHeight());
					setSceneryPacked(true);
					spawnUserActor(actorX, actorY);
					camera.setXY(actorX, actorY);
					startOut();
				} else
				if (state == Overlay.STATE_DONE) {
					removeFromParent();
					switchingRoomsNow = false;
					userActor.setDirection(userActor.getDirectionOnDemend());
				}
			}
		};
		add(overlay);
		switchingRoomsNow = true;
		overlay.startIn();
	}
	
	public int getRoomId() {
		return roomId;
	}

	private final void clear() {
		while(!gameObjects.isEmpty()) {
			final IGameObject gameObject = gameObjects.remove(0);
			final DisplayObject displayObject = (DisplayObject)gameObject;
			
			if(displayObject.hasParent())
				displayObject.getParent().remove(displayObject);
		}
		
		for (final WorldLayer layer : layers) {
			layer.removeAllChildren();
		}
	}

	@Override
	public WorldLayer getLayer(int layerNumber) {
		return layers[layerNumber];
	}

	@Override
	public void setPlayMode(boolean value) {
		this.playMode = value;
		setRoomRectVisible(!value);
	}

	@Override
	public boolean isPlayMode() {
		return playMode;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public final UserActor getUserActor() {
		 return userActor;
	}
	
	public final void createUserActor() {
		removeUserActor();
		userActor = new Ava() {
			@Override
			public void onEachFrame() {
				camera.setDirection(getDirection());
				super.onEachFrame();
			}
			
			@Override
			public void onHealthChanged(int health) {
				onUserActorHealthChanged(health);
			}
		};
		addGameObject(userActor, 5, false);
	}
	
	public void onUserActorHealthChanged(float health) {
		
	}

	public final void removeUserActor() {
		if(userActor != null) {
			removeGameObject(userActor, false);
		}
		if(camera.getAttachedTo() == userActor) camera.setAttachedTo(null);
	}
	
	public final void spawnUserActor(float x, float y) {
		
		System.out.println("x: " + x + ", y: " + y);
		
		userActor.setXY(x, y);
		removeGameObject(userActor, false);
		addGameObject(userActor, 5, false);
		userActor.setAnimation(AKey.IDLE);
		camera.setAttachedTo(userActor);
	}

	public WorldListener getWorldListener() {
		return listener;
	}

	public void setWorldListener(WorldListener listener) {
		this.listener = listener;
	}
	
	public final void actorAttack(final Actor actor, final Weapon weapon) {
        final Bullet bullet = weapon.getNextBullet();

        final boolean left = false;
        bullet.setXY(
            actor.getX() + actor.getWeaponX() * (left ? -1f : 1f),
            actor.getY() + actor.getWeaponY()
        );

        addGameObject(bullet, 5, false);
    }

	public final AreaCheckpoint detectStartCHeckpoint() {
		for( int i = 0 ; i < gameObjects.size(); i ++ ) {
			final IGameObject g = gameObjects.get(i);
			if(g instanceof AreaCheckpoint) {
				final AreaCheckpoint ac = (AreaCheckpoint) g;
				if(ac.getCheckPointType() == AreaCheckpoint.CHECKPOINT_TYPE_START) return ac;
			}
		}
		
		return null;
	}

}
