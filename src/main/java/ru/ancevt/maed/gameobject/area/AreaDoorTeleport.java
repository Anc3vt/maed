package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class AreaDoorTeleport extends Area {

	private static final Color COLOR_FILL = Color.DARK_GREEN;
	private static final Color COLOR_STROKE = Color.BLACK;

	private int targetRoomId, targetX, targetY;

	public AreaDoorTeleport(final MapkitItem mapKitItem, final int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);
	}

	@Override
	public int getAreaType() {
		return Area.DOOR_TELEPORT;
	}

	@Override
	public Area copy() {
		final AreaDoorTeleport result = new AreaDoorTeleport(getMapkitItem(),
				World.getWorld().getMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		return result;
	}

	public int getTargetY() {
		return targetY;
	}

	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}

	public int getTargetX() {
		return targetX;
	}

	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}

	public int getTargetRoomId() {
		return targetRoomId;
	}

	public void setTargetRoom(int targetRoom) {
		this.targetRoomId = targetRoom;
	}

	public final void setTargetLocation(final int x, final int y) {
		setTargetX(x);
		setTargetY(y);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + getId() + "[" + targetRoomId + "," + targetX + "," + targetY + "]";
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
}