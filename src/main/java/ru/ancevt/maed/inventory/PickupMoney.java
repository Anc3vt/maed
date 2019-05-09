package ru.ancevt.maed.inventory;

import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class PickupMoney extends Pickup {

	private String iconKey;
	private int money;
	
	public PickupMoney(MapkitItem mapkitItem, int gameObjectId, String iconKey, int money) {
		super(mapkitItem, gameObjectId);
		setIcon(iconKey);
		this.iconKey = iconKey;
		this.money = money;
	}

	@Override
	public IGameObject copy() {
		final PickupMoney r = new PickupMoney(getMapkitItem(), Map.getCurrentMap().getNextFreeGameObjectId(), iconKey, money);
		r.setXY(getX(), getY());
		return null;
	}
	
	@Override
	public void onUserPickup(UserActor userActor) {
		userActor.addMoney(money);
		super.onUserPickup(userActor);
	}

}
