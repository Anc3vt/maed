package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class UserActor extends Actor {

	public UserActor(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
	}

	@Override
	public void onDamage(IDamaging damagingFrom) {
		World.getWorld().getCamera().setZoom(1.5f);
		super.onDamage(damagingFrom);
	}
}
