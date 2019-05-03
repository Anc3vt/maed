package ru.ancevt.maed.common;

import ru.ancevt.maed.inventory.Pickup;

public interface GameListener {
	void onPuckUpPickup(Pickup pickup);
	void onUserActorSpawn(int roomId, float x, float y);
	void onUserActorDeath();
	void onRoomSwitched(int oldRoomId, int newRoomId);
	void onUserActorOpenDoor();
	
}
