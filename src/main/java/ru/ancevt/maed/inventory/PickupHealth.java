
package ru.ancevt.maed.inventory;

import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.map.MapkitItem;

public class PickupHealth extends Pickup {

	private int addToHealth;
	
	public PickupHealth(MapkitItem mapkitItem, int gameObjectId, String iconKey, int addToHealth) {
		super(mapkitItem, gameObjectId);
		setIcon(iconKey);
		this.addToHealth = addToHealth;
	}
	
	@Override
	public void onUserPickup(UserActor userActor) {
		userActor.addHealth(addToHealth);
	}

}
