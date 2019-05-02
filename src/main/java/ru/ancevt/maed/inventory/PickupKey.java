package ru.ancevt.maed.inventory;

import ru.ancevt.maed.map.MapkitItem;

public class PickupKey extends Pickup {

	public PickupKey(MapkitItem mapkitItem, int gameObjectId, String textureKey, int keyType) {
		super(mapkitItem, gameObjectId);
		setIcon(textureKey);
	}

}
