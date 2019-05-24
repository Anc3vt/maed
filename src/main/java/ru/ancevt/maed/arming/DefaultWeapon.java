package ru.ancevt.maed.arming;

import ru.ancevt.maed.gameobject.Actor;

public class DefaultWeapon extends Weapon {

	private final Bullet[] pool = {
		new DefaultBullet(),
		new DefaultBullet(),
		new DefaultBullet(),
		new DefaultBullet(),
		new DefaultBullet(),
	};
	
	private int increment;
	
	@Override
	public Bullet createBullet(Actor owActor) {
		final Bullet bullet = getBulletFromPool();
		bullet.prepare();
		bullet.setDamagingOwnerActor(owActor);
		return bullet;
	}
	
	private Bullet getBulletFromPool() {
		final Bullet result = pool[increment++];
		if(increment > pool.length - 1) increment = 0;
		return result;
	}

	@Override
	public int getType() {
		return WeaponType.DEFAULT;
	}
	
}
