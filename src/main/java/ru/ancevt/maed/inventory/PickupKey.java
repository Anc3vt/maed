package ru.ancevt.maed.inventory;

import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.map.MapkitItem;

public class PickupKey extends Pickup {

	private int keyType;
	
	public PickupKey(MapkitItem mapkitItem, int gameObjectId, String textureKey, int keyType) {
		super(mapkitItem, gameObjectId);
		setIcon(textureKey);
		this.keyType = keyType;
	}

	@Override
	public InventoryItem getInventoryItem() {
		return new InventoryItemKey(keyType);
	}
	
	@Override
	public void onUserPickup(UserActor userActor) {
		userActor.getInventory().put(getInventoryItem());
		super.onUserPickup(userActor);
	}
}
