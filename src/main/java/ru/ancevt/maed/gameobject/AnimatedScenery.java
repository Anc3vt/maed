package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class AnimatedScenery extends Sprite implements IGameObject,  
	IRepeatable, IScalable, IAlphable, IRotatable{

	private static final int DEFAULT_SLOWING = 10;
	
	private final MapkitItem mapkitItem;
	private final int id;
	private int slowing;
	private int currentFrame;

	private int slowingCounter;
	
	AnimatedScenery(final MapkitItem mapkitImem, int gameObjectId) {
		super(mapkitImem.getTexture(AKey.IDLE, 0));
		this.mapkitItem = mapkitImem;
		id = gameObjectId;
		
		slowing = DEFAULT_SLOWING;
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
		return mapkitItem;
	}

	@Override
	public IGameObject copy() {
		final AnimatedScenery animatedScenery = new AnimatedScenery(getMapkitItem(), Map.getCurrentMap().getNextFreeGameObjectId());
		animatedScenery.setXY(getX(), getY());
		animatedScenery.setRepeat(getRepeatX(), getRepeatY());
		animatedScenery.setRotation(getRotation());
		animatedScenery.setScale(getScaleX(), getScaleY());
		animatedScenery.setAlpha(getAlpha());
		return animatedScenery;
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

	public int getSlowing() {
		return slowing;
	}

	public void setSlowing(int slowing) {
		this.slowing = slowing;
	}
	
	private final void nextFrame() {
		final float frameCount = mapkitItem.getTextureCount(AKey.IDLE);
		currentFrame ++;
		if(currentFrame >= frameCount) currentFrame = 0;
		setTexture(mapkitItem.getTexture(AKey.IDLE, currentFrame));
	}

	@Override
	public void process() {
		slowingCounter ++;
		if (slowingCounter >= slowing) {
			slowingCounter = 0;
			nextFrame();
		}
	}
}









