package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.common.BorderedRect;
import ru.ancevt.d2d2.display.text.BitmapText;
import ru.ancevt.maed.common.DataKey;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.map.DataLine;
import ru.ancevt.maed.map.MapkitItem;

abstract public class Area extends BorderedRect implements IGameObject, ICollisioned {
	
	private static final float DEFAULT_WIDTH = 16;
	private static final float DEFAULT_HEIGHT = 16;
	private static final float ALPHA = 0.25f;
	
	public static final int COLLISION 		= 1;
	public static final int DOOR_TELEPORT 	= 2;
	public static final int DAMAGING  		= 3;
	public static final int CHECKPOINT		= 4;
	public static final int HOOK			= 5;
	public static final int TRIGGER			= 6;
	
	private MapkitItem mapKitItem;
	private final int id;

	protected BitmapText bitmapText;

	private boolean collisionEnabled;
	
	protected Area(final MapkitItem mapKitItem, final int gameObjectId) {
		this.mapKitItem = mapKitItem;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setAlpha(ALPHA);
		id = gameObjectId;
		
		bitmapText = new BitmapText();
		add(bitmapText);
		setCollisionEnabled(true);
	}

	@Override
	public int getId() {
		return id;
	}
	
	abstract public int getAreaType();
	
	@Override
	public boolean isSaveable() {
		return true;
	}
	
	@Override
	public MapkitItem getMapkitItem() {
		return mapKitItem;
	}
	
	public static final Area createArea(final MapkitItem mapkitItem, final int gameObjectId) {
		final DataLine dataLine = mapkitItem.getDataLine();
		final int type = dataLine.getInt(DataKey.AREA_TYPE);
		
		switch (type) {
			case COLLISION:  return new AreaCollision(mapkitItem, gameObjectId);
			case DAMAGING:   return new AreaDamaging(mapkitItem, gameObjectId);
			case CHECKPOINT: return new AreaCheckpoint(mapkitItem, gameObjectId);
			case DOOR_TELEPORT: return new AreaDoorTeleport(mapkitItem,     gameObjectId);
			case HOOK: return new AreaHook(mapkitItem, gameObjectId);
			case TRIGGER: return new AreaTrigger(mapkitItem, gameObjectId);
			
			default:
				return null;
		}
	}
	
	public final void setText(final Object o) {
		bitmapText.setText(o + new String());
	}
	
	public final String getText() {
		return bitmapText.getText();
	}
	
	@Override
	public void setCollisionEnabled(boolean b) {
		collisionEnabled= b;
	}

	@Override
	public boolean isCollisionEnabled() {
		return collisionEnabled;
	}

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		
	}

	@Override
	public float getCollisionX() {
		return 0.0f;
	}

	@Override
	public float getCollisionY() {
		return 0.0f;
	}

	@Override
	public float getCollisionWidth() {
		return getWidth();
	}

	@Override
	public float getCollisionHeight() {
		return getHeight();
	}
	
	@Override
	public void setCollisionVisible(boolean b) {
		//setVisible(b);
	}
	
	@Override
	public boolean isCollisionVisible() {
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + id;
	}
	
	@Override
	public void onCollide(ICollisioned collideWith) {
		
	}
	
	
}
