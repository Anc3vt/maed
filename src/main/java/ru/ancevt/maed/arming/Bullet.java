package ru.ancevt.maed.arming;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.debug.CollisionShowRect;
import ru.ancevt.maed.gameobject.Actor;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IDamaging;
import ru.ancevt.maed.gameobject.IDirectioned;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.map.MapkitItem;

abstract public class Bullet extends DisplayObjectContainer implements IDamaging, IDirectioned {

	
	private int damagingPower;
	private Actor ownerActor;
	private float collX, collY, collW, collH;
	private CollisionShowRect collisionShowRect;
	private boolean collisionVisible;
	private int direction;
	
	public Bullet() {
		
	}
	
	abstract public void prepare();
	
	@Override
	public int getId() {
		return 0;
	}

	@Override
	public boolean isSaveable() {
		return false;
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
	public void process() {

	}

	@Override
	public void setCollisionEnabled(boolean value) {
		// has no effect
	}

	@Override
	public boolean isCollisionEnabled() {
		return true;
	}

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		collX = x;
		collY = y;
		collW = w;
		collH = h;
	}

	@Override
	public float getCollisionX() {
		return collX;
	}

	@Override
	public float getCollisionY() {
		return collY;
	}

	@Override
	public float getCollisionWidth() {
		return collW;
	}

	@Override
	public float getCollisionHeight() {
		return collH;
	}

	@Override
	public void setCollisionVisible(boolean b) {
		this.collisionVisible = b;

		if(collisionShowRect != null) collisionShowRect.removeFromParent();
		
		if(collisionVisible) {
			collisionShowRect = new CollisionShowRect(this);
			add(collisionShowRect);
		} 
	}

	@Override
	public boolean isCollisionVisible() {
		return collisionVisible;
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setDamagingPower(int damagingPower) {
		this.damagingPower = damagingPower;
	}

	@Override
	public int getDamagingPower() {
		return damagingPower;
	}

	@Override
	public void setDamagingOwnerActor(Actor ownerActor) {
		this.ownerActor = ownerActor;
	}

	@Override
	public Actor getDamagingOwnerActor() {
		return ownerActor;
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

	@Override
	public void setStartDirection(int dir) {
	}

	@Override
	public int getStartDirection() {
		return 1;
	}
	
}
