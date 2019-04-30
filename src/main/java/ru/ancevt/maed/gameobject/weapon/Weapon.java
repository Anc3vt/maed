package ru.ancevt.maed.gameobject.weapon;

import ru.ancevt.maed.gameobject.Actor;

abstract public class Weapon {

	protected static final int BULLET_PULL_SIZE = 10;
	
	public static final int TYPE_DEFAULT = 0;
	
	private Bullet[] bulletPull;
	private Actor owner;
	private int bulletIterator;
	
	public Weapon() {
		bulletPull = createPull();
	}
	
	protected Bullet[] createPull() {
		final Bullet[] result = new Bullet[BULLET_PULL_SIZE];
		for(int i = 0; i < result.length; i ++) {
			result[i] = createBullet();
		}
		
		return result;
	}
	
	abstract protected Bullet createBullet();
	
	public Bullet getNextBullet() {
		final Bullet result = bulletPull[bulletIterator];
		result.prepare();
		
		bulletIterator++;
		if(bulletIterator >= bulletPull.length)
			bulletIterator = 0;
		
		return result;
	}
	
	
	public static final Weapon createWeapon(final int weaponTypeId) {
		switch (weaponTypeId) {
			case TYPE_DEFAULT:
				return new DefaultWeapon();
				
			default: return null;
		}
	}

	public Actor getOwner() {
		return owner;
	}

	public void setOwner(Actor owner) {
		this.owner = owner;
		for(int i = 0; i < bulletPull.length; i ++)
			bulletPull[i].setDamagingOwnerActor(owner);
	}
}
