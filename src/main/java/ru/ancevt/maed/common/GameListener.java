package ru.ancevt.maed.common;

import ru.ancevt.maed.inventory.Pickup;

public interface GameListener {
	void onPuckUpPickup(Pickup pickup);
	void onUserActorSpawn();
	void onUserActorDeath();
	void onRoomSwitched();
	
}
