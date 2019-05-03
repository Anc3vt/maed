package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.time.Timer;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.common.Game;
import ru.ancevt.maed.inventory.Inventory;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class DynamicDoor extends Sprite implements IDynamic, ITight {

	private MapkitItem mapkitItem;
	private int gameObjectId;
	private boolean isOpen;
	private int keyTypeId;
	private float collX, collY, collWidth, collHeight;
	
	public DynamicDoor(MapkitItem mapkitItem, int gameObjectId) {
		this.mapkitItem = mapkitItem;
		this.gameObjectId = gameObjectId;
		
		setTexture(mapkitItem.getTexture());
	}
	
	public void open() {
		setTexture(mapkitItem.getTexture(AKey.IDLE, 1));

		final Timer timer = new Timer(200) {
			@Override
			public void onTimerTick() {
				setTexture(mapkitItem.getTexture(AKey.IDLE, 2));
				isOpen = true;
				super.onTimerTick();
			}
		};
		timer.setLoop(false);
		timer.start();
		
	}
	
	public void close() {
		
	}
	
	@Override
	public int getId() {
		return gameObjectId;
	}

	@Override
	public boolean isSaveable() {
		return true;
	}

	@Override
	public MapkitItem getMapkitItem() {
		return mapkitItem;
	}

	@Override
	public IGameObject copy() {
		final DynamicDoor door = new DynamicDoor(mapkitItem, Map.getCurrentMap().getNextFreeGameObjectId());
		door.setXY(getX(), getY());
		
		return door;
		
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCollisionEnabled(boolean value) {
		// has no effect
	}

	@Override
	public boolean isCollisionEnabled() {
		return !isOpen;
	}

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		collX = x;
		collY = y;
		collWidth = w;
		collHeight = h;
	}

	@Override
	public float getCollisionX() {
		return collX;
	}

	@Override
	public float getCollisionY() {
		return collY;
	}

	@Override
	public float getCollisionWidth() {
		return collWidth;
	}

	@Override
	public float getCollisionHeight() {
		return collHeight;
	}

	@Override
	public void setCollisionVisible(boolean b) {
		
	}

	@Override
	public boolean isCollisionVisible() {
		return false;
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		if(collideWith instanceof UserActor) {
			final UserActor userActor = (UserActor)collideWith;
			
			if(!isOpen) {
				final Inventory inventory = userActor.getInventory();
				final int slot = inventory.keySlot(keyTypeId);
				if(slot != -1) {
					open();
					inventory.clearSlot(slot);
					Game.gameListener.onUserActorOpenDoor();
				}
				
			}
		}
	}

	@Override
	public void setFloorOnly(boolean b) {
		
	}

	@Override
	public boolean isFloorOnly() {
		return false;
	}

	@Override
	public void setPushable(boolean b) {
		
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	public int getKeyTypeId() {
		return keyTypeId;
	}

	public void setKeyTypeId(int keyId) {
		this.keyTypeId = keyId;
	}
	
}
