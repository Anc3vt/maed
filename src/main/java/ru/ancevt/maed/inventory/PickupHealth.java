
package ru.ancevt.maed.inventory;

import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class PickupHealth extends Pickup {

	private int addToHealth;
	private String iconKey;
	
	public PickupHealth(MapkitItem mapkitItem, int gameObjectId, String iconKey, int addToHealth) {
		super(mapkitItem, gameObjectId);
		setIcon(iconKey);
		this.iconKey = iconKey;
		this.addToHealth = addToHealth;
	}
	
	@Override
	public void onUserPickup(UserActor userActor) {
		userActor.addHealth(addToHealth);
		super.onUserPickup(userActor);
	}

	@Override
	public IGameObject copy() {
		final PickupHealth pickupHealth = new PickupHealth(getMapkitItem(),
				World.getWorld().getMap().getNextFreeGameObjectId(), iconKey, addToHealth);
		
		pickupHealth.setXY(getX(), getY());
		
		return pickupHealth;
	}
}
