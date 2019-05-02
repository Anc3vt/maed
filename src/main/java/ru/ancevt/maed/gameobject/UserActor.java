package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.inventory.Inventory;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class UserActor extends Actor {

	private Inventory inventory;
	
	public UserActor(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
		inventory = new Inventory(8);
	}

	@Override
	public void onDamage(IDamaging damagingFrom) {
		World.getWorld().getCamera().setZoom(1.5f);
		super.onDamage(damagingFrom);
	}
	
	@Override
	public void setDirection(int direction) {
		World.getWorld().getCamera().setDirection(direction);
		super.setDirection(direction);
	}
}
