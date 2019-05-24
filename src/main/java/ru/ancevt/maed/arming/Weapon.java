package ru.ancevt.maed.arming;

import ru.ancevt.maed.gameobject.Actor;

abstract public class Weapon {
	
	private static final int DEFAULT_BULLET_COUNT = -1;
	
	private int bulletCount;
	private Actor ownerActor;
	
	public Weapon() {
		bulletCount = DEFAULT_BULLET_COUNT;
	}
	
	public void setOwnerActor(Actor ownerActor) {
		this.ownerActor = ownerActor;
	}
	
	public Actor getOwnerActor() {
		return ownerActor;
	}
	
	public void setBulletCount(int bulletCount) {
		this.bulletCount = bulletCount;
	}
	
	public int getBulletCount() {
		return bulletCount;
	}
	
	public abstract Bullet createBullet(Actor owActor);

	public abstract int getType();
	
}
