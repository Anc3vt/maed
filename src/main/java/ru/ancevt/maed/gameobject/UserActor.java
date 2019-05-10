package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.arming.DefaultWeapon;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.common.Game;
import ru.ancevt.maed.gameobject.area.AreaCheckpoint;
import ru.ancevt.maed.inventory.Inventory;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class UserActor extends Actor implements IResettable {

	private int money;
	private Inventory inventory;
	private AreaCheckpoint lastContinueCheckpoint;
	
	public UserActor(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
		inventory = new Inventory(4);
		setWeapon(new DefaultWeapon());
	}

	@Override
	public void setDirection(int direction) {
		World.getWorld().getCamera().setDirection(direction);
		super.setDirection(direction);
	}
	
	@Override
	public void reset() {
		//inventory.clear();
		setHealth(getMaxHealth());
		setCollisionArea(-8,-16,16,32);
		super.reset();
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	@Override
	public void setHealth(int health) {
		super.setHealth(health);
		
		if(health <= 0) {
			Game.mode.onUserActorDeath();
			death();
		}
		if(Game.mode != null) Game.mode.onUserActorHealthChanged(health);
	}
	
	private final void death() {
		setAnimation(AKey.DAMAGE);
		setRotation(-90);
		isDeath = true;
		setCollisionArea(0, 0, 8, 8);
		Game.mode.onUserActorDeath();
	}
	
	@Override
	public void onCollide(ICollisioned collideWith) {
		if(collideWith instanceof DynamicDoor) {
			final DynamicDoor dynamicDoor = (DynamicDoor)collideWith;
			Game.mode.onUserActorCollideDoor(dynamicDoor);
		}
		if(collideWith instanceof AreaCheckpoint) {
			Game.mode.onUserActorCollideCheckpoint((AreaCheckpoint)collideWith);
		}
		super.onCollide(collideWith);
	}

	public void setLastContinueCheckPoint(AreaCheckpoint cp) {
		this.lastContinueCheckpoint = cp;
	}

	public AreaCheckpoint getLastContinueCheckPoint() {
		return lastContinueCheckpoint;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
		Game.mode.onUserActorMoneyChange(money);
	}
	
	public void addMoney(int money) {
		setMoney(getMoney() + money);
	}
}
