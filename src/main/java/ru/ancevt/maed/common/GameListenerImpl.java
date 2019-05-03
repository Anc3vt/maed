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
	private InventoryView inventoryView;
	
	public GameListenerImpl(Root root, World world) {
		userActor = world.getUserActor();
		inventoryView = new InventoryView(userActor.getInventory());
		inventoryView.setScale(2f, 2f);
		root.add(inventoryView, 10, 200);
	}
	
	@Override
	public void onPuckUpPickup(Pickup pickup) {
		System.out.println("Picked up: " + pickup);
		inventoryView.update();
	}

	@Override
	public void onUserActorSpawn(int roomId, float x, float y) {
		System.out.println("User actor spawn, room: " + roomId + ", x: " + x + ", y: " + y);
	}

	@Override
	public void onUserActorDeath() {
		System.out.println("User actor death");
	}

	@Override
	public void onRoomSwitched(int oldRoomId, int newRoomId) {
		System.out.println("Room switched from " + oldRoomId + " to " + newRoomId);
	}

	@Override
	public void onUserActorOpenDoor() {
		inventoryView.update();
	}
	
	

}
