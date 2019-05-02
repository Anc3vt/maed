
package ru.ancevt.maed.inventory;

import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.map.MapkitItem;

public class PickupHealth extends Pickup {

	public PickupHealth(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
		setIcon("p_health");
		
	}
	
	@Override
	public void onUserPickup(UserActor userActor) {
		userActor.addHealth(50);
	}

}
