package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.gameobject.IDirectioned;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class AreaCheckpoint extends Area implements IDirectioned {

	private static final Color COLOR_FILL = Color.GREEN;
	private static final Color COLOR_STROKE = Color.WHITE;

	private static final String STR_START = "Start";
	private static final String STR_FINISH = "Finish";
	private static final String STR_CONTINUE = "Continue";
	private static final String STR_COMMON = "Common";

	private static final int DEFAULT_DIRECTION = 1;

	public static final int CHECKPOINT_TYPE_START = 0;
	public static final int CHECKPOINT_TYPE_FINISH = 1;
	public static final int CHECKPOINT_TYPE_CONTINUE = 2;
	public static final int CHECKPOINT_TYPR_COMMON = 3;

	private int checkPointType;

	private int direction;

	public AreaCheckpoint(final MapkitItem mapKitItem, final int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);

		setDirection(DEFAULT_DIRECTION);
		setCheckPointType(CHECKPOINT_TYPE_START);
	}

	@Override
	public int getAreaType() {
		return Area.CHECKPOINT;
	}

	public int getCheckPointType() {
		return checkPointType;
	}

	public void setCheckPointType(int checkPointType) {
		this.checkPointType = checkPointType;
		updateText();
	}

	@Override
	public Area copy() {
		final AreaCheckpoint result = new AreaCheckpoint(getMapkitItem(), Map.getCurrentMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		result.setCheckPointType(getCheckPointType());
		return result;
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
		updateText();
	}

	@Override
	public int getDirection() {
		return direction;
	}

	private final void updateText() {
		final StringBuilder s = new StringBuilder();

		if (getDirection() == -1) {
			s.append("<- ");
		}
		if (getDirection() == 1) {
			s.append("-> ");
		}

		switch (getCheckPointType()) {
		case CHECKPOINT_TYPE_START:
			s.append(STR_START);
			break;
		case CHECKPOINT_TYPE_FINISH:
			s.append(STR_FINISH);
			break;
		case CHECKPOINT_TYPE_CONTINUE:
			s.append(STR_CONTINUE);
			break;
		case CHECKPOINT_TYPR_COMMON:
			s.append(STR_COMMON);
			break;
		}

		setText(s.toString());
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + getId() +  "[" + getCheckPointType() + "]";
	}

	@Override
	public void setStartDirection(int dir) {
		
	}

	@Override
	public int getStartDirection() {
		return 1;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
}
