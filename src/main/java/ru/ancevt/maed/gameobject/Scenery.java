package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class Scenery extends Sprite implements IGameObject, IRepeatable, IRotatable, IScalable, IAlphable {

	private final MapkitItem mapKititem;
	private final int id;

	Scenery(final MapkitItem mapkitItem, final int gameObjectId) {
		super(mapkitItem.getTexture(AKey.IDLE, 0));
		this.mapKititem = mapkitItem;
		id = gameObjectId;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean isSaveable() {
		return true;
	}

	@Override
	public MapkitItem getMapkitItem() {
		return mapKititem;
	}

	@Override
	public Scenery copy() {
		final Scenery scenery = new Scenery(getMapkitItem(), World.getWorld().getMap().getNextFreeGameObjectId());
		scenery.setXY(getX(), getY());
		scenery.setRepeat(getRepeatX(), getRepeatY());
		scenery.setRotation(getRotation());
		scenery.setScale(getScaleX(), getScaleY());
		scenery.setAlpha(getAlpha());
		return scenery;
	}

	@Override
	public float getWidth() {
		return getOriginalWidth() * getRepeatX();
	}

	@Override
	public float getHeight() {
		return getOriginalHeight() * getRepeatY();
	}

	@Override
	public float getOriginalWidth() {
		return getTexture().getWidth();
	}

	@Override
	public float getOriginalHeight() {
		return getTexture().getHeight();
	}

	@Override
	public void process() {
		
	}

}
