package ru.ancevt.maed.inventory;

import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class PickupKey extends Pickup {

	private int keyType;
	private String textureKey;
	
	public PickupKey(MapkitItem mapkitItem, int gameObjectId, String textureKey, int keyType) {
		super(mapkitItem, gameObjectId);
		setIcon(textureKey);
		this.textureKey = textureKey;
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
	
	@Override
	public IGameObject copy() {
		final PickupKey result = new PickupKey(getMapkitItem(), 
				World.getWorld().getMap().getNextFreeGameObjectId(), textureKey, keyType);
	
	
		result.setXY(getX(), getY());
		return result;
	} 
}
