package ru.ancevt.maed.gameobject.weapon;

public class DefaultWeapon extends Weapon {

	
	@Override
	protected Bullet createBullet() {
		return new DefaultBullet();
	}
	

}
