package ru.ancevt.maed.editor;

import java.io.File;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.gameobject.GameObjectFactory;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.world.World;

public class EditorInfoText extends DisplayObjectContainer {
	private final ShadowLabel slCamera;
	private final ShadowLabel slWorld;
	private final ShadowLabel slLayer;
	private final ShadowLabel slOther;
	
	private final World world;
	
	public EditorInfoText(World world) {
		this.world = world;
		
		slCamera = new ShadowLabel();
		slCamera.setColor(Color.YELLOW);
		add(slCamera, 5, 5);
		
		slWorld = new ShadowLabel();
		slWorld.setColor(Color.GREEN);
		add(slWorld, 5, 20);
		
		slLayer = new ShadowLabel();
		slLayer.setColor(Color.WHITE);
		add(slLayer, 5, 35);
		
		slOther = new ShadowLabel();
		slOther.setColor(Color.WHITE);
		add(slOther, 5, 50);
	}
	
	public void savedMap(File file) {
		slOther.setText("Saved: " + file.getAbsolutePath());
	}
	
	public void updateOther(String text) {
		slOther.setText(text);
	}
	
	public void selectGameObject(IGameObject gameObject) {
		slOther.setText(gameObject.toString() + " " + GameObjectFactory.gameObjectToDataLineString(gameObject, 0));
	}
	
	public void mouseUpdate(int x, int y) {
		slOther.setText(x + ", " + y);
	}
	
	@Override
	public void onEachFrame() {
		updateBTCamera();
		updateBTWorld();
		super.onEachFrame();
	}
	
	private void updateBTCamera() {
		final StringBuilder s = new StringBuilder();
		s.append("Camera ");
		s.append("(" + (int)world.getCamera().getX() + ", " + (int)world.getCamera().getY() + ")");
		s.append(", zoom: ");
		s.append(round(world.getCamera().getZoom()));
		
		if(world.getCamera().isBoundsLock()) s.append(", lock");
		
		slCamera.setText(s.toString());
	}
	
	private final String round(float n) {
		String string = String.valueOf(n);
		if(string.length() > 3) string = string.substring(0, 3);
		return string;
	}
	
	private void updateBTWorld() {
		final StringBuilder s = new StringBuilder();
		s.append("Objects: " + world.getGameObjectsCount());
		slWorld.setText(s.toString());
	}
	
	public void updateLayer(int layerNum, int layerObjectCount) {
		slLayer.setText("Layer #" + layerNum);
	}
}
