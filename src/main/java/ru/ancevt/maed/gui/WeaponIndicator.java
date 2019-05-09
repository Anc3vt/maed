package ru.ancevt.maed.gui;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.arming.Weapon;

public class WeaponIndicator extends DisplayObjectContainer {

	private Weapon weapon;
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
}
