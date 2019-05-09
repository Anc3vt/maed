package ru.ancevt.maed.common;

import java.io.IOException;

import ru.ancevt.d2d2.common.D2D2;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Root;
import ru.ancevt.maed.gameobject.area.AreaCheckpoint;
import ru.ancevt.maed.gui.GameGUI;
import ru.ancevt.maed.gui.VisualController;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapLoader;
import ru.ancevt.maed.map.Room;
import ru.ancevt.maed.world.Camera;
import ru.ancevt.maed.world.World;
import ru.ancevt.maed.world.WorldListener;

public class GameRoot extends Root implements WorldListener {
	
	private World world;
	private String mapToLoad;
	private DisplayObjectContainer rootLayer;
	private DisplayObjectContainer cameraLayer;
	private GameGUI gameGui;
	
	public GameRoot() {
		world = new World() {
			
			@Override
			public void onMapSet() {
				float startX = 0, startY = 0;
				final AreaCheckpoint startCheckpoint = world.getMap().getStartCheckpoint();
				world.getUserActor().setLastContinueCheckPoint(startCheckpoint);
				if(startCheckpoint != null) {
					startX = startCheckpoint.getX();
					startY = startCheckpoint.getY();
				}
				
				world.setSceneryPacked(true);
				world.getUserActor().reset();
				world.spawnUserActor(startX, startY);
				world.getUserActor().setHealth(1000);
				super.onMapSet();
			}
			
		};
		world.setWorldListener(this);
		rootLayer = new DisplayObjectContainer();
		cameraLayer = new DisplayObjectContainer();
		cameraLayer.add(world);
		rootLayer.add(cameraLayer, Viewport.WIDTH / 2, Viewport.HEIGHT / 2);
		add(rootLayer);

		world.getCamera().setAutoZoom(true);
		world.getCamera().setBoundsLock(false);
		world.setRoomRectVisible(true);
		
		gameGui = new GameGUI(world.getUserActor());
		rootLayer.add(gameGui);
		
		Game.root = this;
		Game.rootLayer = rootLayer;
		Game.world = world;
		Game.mode = new GameMode(world, gameGui);
		
		final VisualController vc = new VisualController(PlayerController.getInstance());
		rootLayer.add(vc);
		
		world.setAreasVisible(false);
		world.spawnUserActor(100, 100);
		
		
		final Camera camera = world.getCamera();
		
		camera.setBoundsLock(true);
		rootLayer.setScale(0.1f, 0.1f);
		world.resetAllResettables(true);
		while(Viewport.WIDTH * rootLayer.getScaleX() < D2D2.getRenderer().getWidth()) {
			rootLayer.toScale(1.01f, 1.01f);
		}

		update();
		
		
		setPlayMode(true);
	}
	
	public final void update() {
		final int width = D2D2.getRenderer().getWidth();
		final int height = D2D2.getRenderer().getHeight();
		
		rootLayer.setXY(
			(width - Viewport.WIDTH * rootLayer.getScaleX()) / 2,
			(height - Viewport.HEIGHT * rootLayer.getScaleY()) / 2
		);
	}
	
	public void setPlayMode(boolean playMode) {
		world.resetAllResettables(true);

		PlayerController.getInstance().setEnabled(playMode);
		world.setPlayMode(playMode);
		world.getCamera().setBoundsLock(playMode);
	}
	
	@Override
	public void onEachFrame() {
		if(mapToLoad != null) {
			actualLoadMap(mapToLoad);
			mapToLoad = null;
		}
	}
	
	public void loadMap(String mapFileName) {
		mapToLoad = mapFileName;
	}
	
	private void actualLoadMap(String mapFileName) {
		try {
			final Map map = MapLoader.load("map1.map");
			world.setMap(map);
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onWorldMapChange(Map map) {
		
	}

	@Override
	public void onWorldRoomChange(Room room) {
		
	}
}
