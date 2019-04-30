package ru.ancevt.maed.editor;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ru.ancevt.d2d2.common.BorderedRect;
import ru.ancevt.d2d2.common.D2D2;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Root;
import ru.ancevt.maed.common.CommonPanel;
import ru.ancevt.maed.common.HealthBar;
import ru.ancevt.maed.common.PlayerController;
import ru.ancevt.maed.common.Viewport;
import ru.ancevt.maed.gameobject.area.AreaCheckpoint;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapLoader;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.map.Room;
import ru.ancevt.maed.world.Camera;
import ru.ancevt.maed.world.World;
import ru.ancevt.maed.world.WorldListener;

public class EditorRoot extends Root implements EditorControllerListener, WorldListener {

	private static EditorRoot instance;
	
	public static final EditorRoot getInstance() {
		return instance == null ? instance = new EditorRoot() : instance;
	}
	
	private boolean playMode;
	
	private EditorGUI gui;
	private World world;
	private DisplayObjectContainer rootLayer;
	private DisplayObjectContainer cameraLayer;
	private BorderedRect viewportRect;
	private String mapToLoad;
	private EditorInfoText editorInfoText;
	private Grid grid;
	private int worldMouseX, worldMouseY;
	private GameObjectEditor editor;

	public EditorRoot() {
		viewportRect = new BorderedRect(Viewport.WIDTH, Viewport.HEIGHT, null, Color.DARK_GRAY);
		
		final HealthBar healthbar = new HealthBar();
		
		world = new World() {
			@Override
			public void onUserActorHealthChanged(float health) {
				healthbar.setValue(health);
			}
		};
		world.setWorldListener(this);
		rootLayer = new DisplayObjectContainer();
		cameraLayer = new DisplayObjectContainer();
		cameraLayer.add(world);
		rootLayer.add(cameraLayer, Viewport.WIDTH / 2, Viewport.HEIGHT / 2);
		rootLayer.add(viewportRect);
		add(rootLayer);
		
		world.getCamera().setBoundsLock(false);
		world.setRoomRectVisible(true);

		grid = new Grid();
		world.add(grid);
		
		gui = new EditorGUI() {
			
			@Override
			public void onMapkitItemSelected(MapkitItem mapkitItem) {
				super.onMapkitItemSelected(mapkitItem);
				editor.startPlacing(mapkitItem);
			}
			
			@Override
			public void onSelectRoom(int roomId) {
				world.setRoomId(roomId);
			}
			
			@Override
			public void onUpdateModel() {
				world.setRoomRectVisible(false);
				world.setRoomRectVisible(true);
				world.update();
			}
			
			@Override
			public void onZoomInButtonPressed() {
				world.getCamera().zoom(1.1f);
			}
			
			@Override
			public void onZoomOutButtonPressed() {
				world.getCamera().zoom(0.9f);
			}
		};
		add(gui);
		
		editorInfoText = new EditorInfoText(world);
		add(editorInfoText);
		
		editor = new GameObjectEditor(world, EditorController.getInstance(), editorInfoText);
		
		EditorController.getInstance().setListener(this);
		EditorController.getInstance().start();
		
		update();
		setPlayMode(false);
		
		add(healthbar, 300, 10);
		healthbar.setMax(world.getUserActor().getMaxHealth());
		
		add(CommonPanel.getInstance());
	}
	
	public void loadMap(String mapFileName) {
		mapToLoad = mapFileName;
	}
	
	private void actualLoadMap(String mapFileName) {
		try {
			final Map map = MapLoader.load("map1.map");
			world.setMap(map);
			gui.setMap(map);
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public final void update() {
		final int width = D2D2.getRenderer().getWidth();
		final int height = D2D2.getRenderer().getHeight();
		
		grid.redrawLines();
		
		rootLayer.setXY(
			(width - Viewport.WIDTH * rootLayer.getScaleX()) / 2,
			(height - Viewport.HEIGHT * rootLayer.getScaleY()) / 2
		);
	}
	
	public void setPlayMode(boolean playMode) {
		this.playMode = playMode;
		
		if(playMode) {
			remove(gui);
			world.remove(grid);
		} else {
			add(gui);
			world.add(grid);
			add(Cursor.getInstance());
		}

		PlayerController.getInstance().setEnabled(playMode);
		world.setPlayMode(playMode);
		world.getCamera().setBoundsLock(playMode);
		viewportRect.setVisible(playMode);
		editor.unselect();
	}
	
	public boolean isPlayMode() {
		return playMode;
	}
	
	public final void setGridEnabled(final boolean b) {
        grid.setVisible(b);
    }

    public final boolean isGridEnabled() {
        return grid.isVisible();
    }
	
	public final void saveMap() {
		final File file = new File("test.map");
		try {
			MapSaver.save(world.getMap(), file);
			editorInfoText.savedMap(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onWorldMapChange(Map map) {
		
	}

	@Override
	public void onWorldRoomChange(Room room) {
		gui.setRoom(room);
	}
	
	@Override
	public void onEachFrame() {
		if(mapToLoad != null) {
			actualLoadMap(mapToLoad);
			mapToLoad = null;
		}
		
		editor.process();
	}
	
	@Override
	public void onMouseWheel(int delta) {
		if(delta < 0) {
			rootLayer.toScale(1.1f, 1.1f);
		} else {
			rootLayer.toScale(0.9f, 0.9f);
		}
		update();
	}
	
	public void mouseMoved(int x, int y, boolean drag) {
		final int wX = (int) world.getAbsoluteX();
		final int wY = (int) world.getAbsoluteY();

		final int wmX = x - wX;
		final int wmY = y - wY;
		
		this.worldMouseX = (int)(wmX / world.getAbsoluteScaleX());
		this.worldMouseY = (int)(wmY / world.getAbsoluteScaleY());
		
		editorInfoText.mouseUpdate(worldMouseX, worldMouseY);
		editor.mouseMove(x, y, worldMouseX, worldMouseY, drag);
	}
	
	@Override
	public void onScreenTouchDown(int x, int y, int pointer) {
		editor.mouseDown(x, y, worldMouseX, worldMouseY);
	}
	
	@Override
	public void onScreenTouchUp(int x, int y, int pointer) {
		editor.mouseUp(x, y, worldMouseX, worldMouseY);
	}

	@Override
	public void editorControllerKeyUp(int keyCode, boolean shift, boolean control, boolean alt) {
		editor.keyUp(keyCode);
	}
	
	@Override
	public void editorControllerKeyDown(int keyCode, boolean shift, boolean control, boolean alt) {
		final Camera camera = world.getCamera();
		
		int num = (keyCode - KeyEvent.VK_0 >= 0 && keyCode - KeyEvent.VK_0 <= 9) ? keyCode - KeyEvent.VK_0 : -1;
		
		if(num != -1) {
			editorInfoText.updateLayer(num, 0);
			
		}
		
		editor.keyDown(keyCode);
		
		if(control) {
			switch (keyCode) {
				case KeyEvent.VK_P:
					
					if(shift) {
						editor.applyPlaySettings();
						setGridEnabled(false);
						
						float startX = 0, startY = 0;
						final AreaCheckpoint startCheckpoint = world.detectStartCHeckpoint();
						if(startCheckpoint != null) {
							startX = startCheckpoint.getX();
							startY = startCheckpoint.getY();
						}
						
						world.spawnUserActor(startX, startY);
						world.getUserActor().setHealth(1000);
						world.getUserActor().setAlive(true);
						if(startCheckpoint != null) world.getUserActor().setDirection(startCheckpoint.getDirection());
						
						camera.setBoundsLock(true);
						rootLayer.setScale(0.1f, 0.1f);
						world.setSceneryPacked(true);
						world.resetAllResettables();
						while(Viewport.WIDTH * rootLayer.getScaleX() < D2D2.getRenderer().getWidth()) {
							rootLayer.toScale(1.01f, 1.01f);
						}
						update();
						
					} 
					setPlayMode(!isPlayMode());
					if(!isPlayMode() && world.isSceneryPacked()) world.setSceneryPacked(false);
					
					break;
					
				case KeyEvent.VK_EQUALS:
					camera.zoom(1.1f);
					break;
					
				case KeyEvent.VK_MINUS:
					camera.zoom(0.9f);
					break;
					
				case KeyEvent.VK_B:
					camera.setBoundsLock(!camera.isBoundsLock());
					break;
					
				case KeyEvent.VK_F:
					if(rootLayer.getScaleX() == 1.0f) {
						rootLayer.setScale(0.1f, 0.1f);
						while(Viewport.WIDTH * rootLayer.getScaleX() < D2D2.getRenderer().getWidth()) {
							rootLayer.toScale(1.01f, 1.01f);
						}
					} else {
						rootLayer.setScale(1f, 1f);
					}
					update();
					break;
					
				case KeyEvent.VK_R:
					world.spawnUserActor(worldMouseX, worldMouseY);
					break;
				
					
				case KeyEvent.VK_G:
					setGridEnabled(!isGridEnabled());
					break;
					
				case KeyEvent.VK_S:
					saveMap();
					break;
					 
				case KeyEvent.VK_Z:
					world.setSceneryPacked(!world.isSceneryPacked());
					break;
					
				case KeyEvent.VK_E:
					editor.setEnabled(!editor.isEnabled());
					editorInfoText.updateOther("Editor enabled: " + editor.isEnabled());
					break;
					
				case KeyEvent.VK_NUMPAD1:
					world.switchRoom(1, 16, 16);
					break;
				case KeyEvent.VK_NUMPAD2:
					world.switchRoom(2, 16, 16);
					break;
	
				
			}
		} 
	}


}


























