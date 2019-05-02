package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class AreaWater extends Area{

	private static float DEFAULT_DENSITY = 1f;

	private float density;
		
	protected AreaWater(MapkitItem mapKitItem, int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setDensity(DEFAULT_DENSITY);
	}

	@Override
	public IGameObject copy() {
		final AreaWater result = new AreaWater(getMapkitItem(), 
				Map.getCurrentMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		result.setDensity(getDensity());
		return result;
	}

	@Override
	public void process() {
		final int rnd = (int)(Math.random() * 100);
		if(rnd < 2) {
			createBubble();
		}
	}

	@Override
	public int getAreaType() {
		return Area.WATER;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}
	
	private void createBubble() {
		float x = (float)Math.random() * getWidth();
		for(int i = 0; i < (int)(Math.random() * 3); i ++) {
			final Bubble bubble = new Bubble(this);
			getParent().add(bubble , x + getX(), getHeight() + getY());
			
		}
	}

}

class Bubble extends Sprite {
	private Area area;
	
	public Bubble(Area area) {
		super("bubble");
		
		this.area = area;
		final float scale = (float)Math.random();
		setScale(scale, scale);
		setAlpha((float)Math.random());
	}
	
	@Override
	public void onEachFrame() {
		move((float)Math.random()-0.5f, -0.5f);
		super.onEachFrame();
		if(getY() < area.getY()) removeFromParent();
	}
}

















