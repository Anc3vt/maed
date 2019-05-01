package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class AreaDoorTeleportCp extends Area {

	private static final Color COLOR_FILL = Color.DARK_GREEN;
	private static final Color COLOR_STROKE = Color.BLACK;

	private int targetCheckpointId;

	public AreaDoorTeleportCp(final MapkitItem mapkitItem, final int gameObjectId) {
		super(mapkitItem, gameObjectId);
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);
	}

	@Override
	public int getAreaType() {
		return Area.DOOR_TELEPORT;
	}

	@Override
	public Area copy() {
		final AreaDoorTeleportCp result = new AreaDoorTeleportCp(getMapkitItem(),
				Map.getCurrentMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		return result;
	}

	public void setTargetCheckpointId(int checkpointId) {
		targetCheckpointId = checkpointId;
	}
	
	public int getTargetCheckpointId() {
		return targetCheckpointId;
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