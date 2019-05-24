package ru.ancevt.maed.arming;

import ru.ancevt.maed.common.DataKey;
import ru.ancevt.maed.gameobject.Actor;
import ru.ancevt.maed.map.DataLine;

public class WeaponGiver {
	public static final void arm(Actor actor, DataLine dataLine) {
		final int weaponType = dataLine.getInt(DataKey.WEAPON_TYPE);
		
		switch(weaponType) {
			case WeaponType.DEFAULT: 
				actor.setWeapon(new DefaultWeapon());
				break;
				
			default:
				actor.setWeapon(null);
		}
	}
}
