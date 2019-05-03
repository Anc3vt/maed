package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.common.Game;
import ru.ancevt.maed.inventory.Inventory;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class UserActor extends Actor {

	private Inventory inventory;
	
	public UserActor(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
		inventory = new Inventory(4);
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

	public Inventory getInventory() {
		return inventory;
	}
	
	@Override
	public void setHealth(int health) {
		if(health <= 0) {
			Game.gameListener.onUserActorDeath();
			death();
		}
		super.setHealth(health);
	}
	
	private final void death() {
		setAnimation(AKey.DAMAGE);
		setRotation(-90);
		isDeath = true;
		setCollisionArea(0, 0, 8, 8);
	}

}
