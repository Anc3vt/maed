package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.display.DisplayObject;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.FrameSet;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.map.MapkitItem;

abstract public class Animated extends DisplayObjectContainer implements IAnimated {

	private int direction;
	private int currentAnimationKey;
	private FrameSet[] animations;
	private FrameSet currentFrameSet;
	private final MapkitItem mapkitItem;
	private final int id;
	private int directionOnDemend;
	
	public Animated(final MapkitItem mapKitItem, final int gameObjectId) {
		this.mapkitItem = mapKitItem;
		this.id = gameObjectId;
		
		prepareAnimations();
		setAnimation(AKey.IDLE);
	}
	
	private final void prepareAnimations() {
		animations = new FrameSet[AKey.MAX_ANIMATIONS];
		
		for(int animKey = 0; animKey < AKey.MAX_ANIMATIONS; animKey ++) {
			
			if(!mapkitItem.isAnimationKeyExists(animKey)) continue;
			
			final int framesCount = mapkitItem.getTextureCount(animKey);
			
			final DisplayObject[] frames = new DisplayObject[framesCount];
			
			for(int i = 0; i < framesCount; i ++) {
				final Sprite sprite = new Sprite(
					mapkitItem.getTexture(animKey, i)	
				);
				sprite.setXY(
					-sprite.getWidth()/2,
					-sprite.getHeight()/2
				);
				frames[i] = sprite;
			}
			
			animations[animKey] = new FrameSet(frames);
			animations[animKey].setLoop(true);
			animations[animKey].setSlowing(AKey.SLOWING);
		}
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
		return null;
	}

	@Override
	public int getAnimation() {
		return currentAnimationKey;
	}

	@Override
	public void setAnimation(final int animationKey) {
		setAnimation(animationKey, true);
	}

	@Override
	public void setAnimation(final int animationKey, final boolean loop) {
		this.currentAnimationKey = animationKey;
		
		for(int i = 0; i < animations.length; i ++) {
			final FrameSet fs = currentFrameSet = animations[i];
			
			if(fs == null || !getMapkitItem().isAnimationKeyExists(animationKey)) continue;
			
			if(i != animationKey && currentFrameSet != null && currentFrameSet.hasParent()) 
				currentFrameSet.getParent().remove(currentFrameSet);
			
			if(i == animationKey) {
				fs.setLoop(loop);
				fs.play();
				add(fs);
			}
			
		}
	}
	
	public void setDirectionOnDemand(int direction) {
		directionOnDemend = direction;
	}
	
	public int getDirectionOnDemend() {
		return directionOnDemend;
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
		setScaleX(getDirection());
	}
	
	@Override
	public int getDirection() {
		return direction;
	}

}
