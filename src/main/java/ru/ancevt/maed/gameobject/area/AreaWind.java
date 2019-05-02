package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class AreaWind extends Area {

	private static final Color COLOR_FILL = Color.WHITE;
	private static final Color COLOR_STROKE = Color.BLACK;
	
	protected AreaWind(MapkitItem mapKitItem, int gameObjectId) {
		super(mapKitItem, gameObjectId);
		updateText();
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);
	}

	private float windX, windY;
	
	@Override
	public IGameObject copy() {
		final AreaWind result = new AreaWind(getMapkitItem(), Map.getCurrentMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		result.setWindX(getWindX());
		result.setWindY(getWindY());
		return result;
	}

	@Override
	public void process() {
		
	}

	@Override
	public int getAreaType() {
		return Area.WIND;
	}

	public float getWindX() {
		return windX;
	}

	public void setWindX(float windX) {
		this.windX = windX;
		updateText();	
	}
	
	public float getWindY() {
		return windY;
	}

	public void setWindY(float windY) {
		this.windY = windY;
		updateText();
	}
	
	
	private final void updateText() {
		setText("x: " + windX + ", y: " + windY);
	}

}
