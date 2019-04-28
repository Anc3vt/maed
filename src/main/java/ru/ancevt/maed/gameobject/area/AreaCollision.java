package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.gameobject.ITight;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class AreaCollision extends Area implements ITight {

	private static final Color COLOR_FILL = Color.BLACK, COLOR_FILL_FLOOR_ONLY = Color.WHITE;

	private static final Color COLOR_STROKE = Color.WHITE;

	private boolean floorOnly, pushable;

	public AreaCollision(final MapkitItem mapkitItem, final int gameObjectId) {
		super(mapkitItem, gameObjectId);
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);
		setCollisionEnabled(true);
	}

	public final void setFloorOnly(final boolean floorOnly) {
		this.floorOnly = floorOnly;
		setFillColor(floorOnly ? COLOR_FILL_FLOOR_ONLY : COLOR_FILL);
	}

	public final boolean isFloorOnly() {
		return floorOnly;
	}

	@Override
	public int getAreaType() {
		return Area.COLLISION;
	}

	@Override
	public Area copy() {
		final AreaCollision result = new AreaCollision(getMapkitItem(), 
				Map.getCurrentMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		result.setFloorOnly(isFloorOnly());
		return result;
	}

	@Override
	public void setPushable(boolean b) {
		this.pushable = b;
	}

	@Override
	public boolean isPushable() {
		return pushable;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + getId();
	}
	

	
}
