package ru.ancevt.maed.common;

import ru.ancevt.d2d2.display.Root;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.inventory.InventoryView;
import ru.ancevt.maed.inventory.Pickup;
import ru.ancevt.maed.world.World;

public class GameListenerImpl implements GameListener {
	
	private Root root;
	private World world;
	private UserActor userActor;
	
	public GameListenerImpl(Root root, World world) {
		userActor = world.getUserActor();
		
		InventoryView inventoryView = new InventoryView(userActor.getInventory());
		root.add(inventoryView);
	}
	
	@Override
	public void onPuckUpPickup(Pickup pickup) {
		System.out.println("Picked up: " + pickup);
	}

	@Override
	public void onUserActorSpawn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserActorDeath() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomSwitched() {
		// TODO Auto-generated method stub
		
	}
	
	

}
