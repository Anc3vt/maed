package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class AreaHook extends Area {

	private static final Color COLOR_FILL = Color.LIGHT_GREEN;
	private static final Color COLOR_STROKE = Color.WHITE;

	AreaHook(MapkitItem mapKitItem, int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);
	}

	@Override
	public Area copy() {
		final AreaHook result = new AreaHook(getMapkitItem(), Map.getCurrentMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		return result;
	}

	@Override
	public int getAreaType() {
		return Area.HOOK;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + getId();
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

}
