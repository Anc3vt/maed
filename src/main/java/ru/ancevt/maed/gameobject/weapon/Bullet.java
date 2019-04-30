package ru.ancevt.maed.gameobject.weapon;

import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.gameobject.Actor;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IDirectioned;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.map.MapkitItem;

abstract public class Bullet extends DisplayObjectContainer implements ICollisioned, IDirectioned {
	
	private boolean collisionEnabled;
	private float collisionX, collisionY, collisionWidth, collisionHeight;
	private Actor owner;
	private PlainRect collisionRect;
	private int direction;
	
	Bullet() {
		setCollisionEnabled(true);
		setCollisionArea(-4, -4, 8, 8);
	}
	
	public void setDamagingPower(int damagingPower) {
		throw new RuntimeException("can't assign random damaging power to bullet");
	}

	abstract public void prepare();
	abstract public void destroy();
	
	@Override
	public boolean isSaveable() {
		return false;
	}
	
	@Override
	public int getId() {
		return 0;
	}

	@Override
	public MapkitItem getMapkitItem() {
		return null;
	}

	@Override
	public IGameObject copy() {
		return null;
	}
	
	@Override
	public void setCollisionEnabled(boolean b) {
		collisionEnabled = b;
	}

	@Override
	public boolean isCollisionEnabled() {
		return collisionEnabled;
	}

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		collisionX = x;
		collisionY = y;
		collisionWidth = w;
		collisionHeight = h;
	}

	@Override
	public float getCollisionX() {
		return collisionX;
	}

	@Override
	public float getCollisionY() {
		return collisionY;
	}

	@Override
	public float getCollisionWidth() {
		return collisionWidth;
	}

	@Override
	public float getCollisionHeight() {
		return collisionHeight;
	}

	public void setDamagingOwnerActor(Actor actor) {
		owner = actor;
	}

	public Actor getDamagingOwnerActor() {
		return owner;
	}
	
	@Override
	public void setCollisionVisible(boolean b) {
		if(b) {
			if(isCollisionVisible()) return;
			
			collisionRect = new PlainRect(
				getCollisionWidth(), 
				getCollisionHeight()
			);
			
			collisionRect.setColor(Color.RED);
			collisionRect.setAlpha(0.25f);
			collisionRect.setXY(getCollisionX(), getCollisionY());
			add(collisionRect);
		} else {
			if(collisionRect != null && collisionRect.hasParent()) {
				collisionRect.getParent().remove(collisionRect);
			}
			collisionRect = null;
		}
	}
	
	@Override
	public boolean isCollisionVisible() {
		return collisionRect != null;
	}
	
	@Override
	public void setDirection(int direction) {
		this.direction = direction;
		setScaleX(direction);
	}
	
	@Override
	public int getDirection() {
		return direction;
	}
}
