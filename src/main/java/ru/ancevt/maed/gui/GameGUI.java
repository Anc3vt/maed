package ru.ancevt.maed.gui;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.arming.Weapon;
import ru.ancevt.maed.common.HealthBar;
import ru.ancevt.maed.common.Viewport;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.inventory.InventoryView;

public class GameGUI extends DisplayObjectContainer {
	private MoneyIndicator moneyIndicator;
	private WeaponIndicator weaponIndicator;
	private InventoryView inventoryView;
	private HealthBar healthBar;
	
	public GameGUI(UserActor userActor) {
		moneyIndicator = new MoneyIndicator();
		weaponIndicator = new WeaponIndicator();
		inventoryView = new InventoryView(userActor.getInventory());
		healthBar = new HealthBar();
		
		add(moneyIndicator, 10, 10);
		add(weaponIndicator, 200, 10);
		add(healthBar, 80, 10);
		add(inventoryView, Viewport.WIDTH - inventoryView.getWidth(), 0);
		
		healthBar.setMax(50);
		healthBar.setValue(50);
	}
	
	public final void setMoney(int money) {
		moneyIndicator.setMoney(money);
	}
	
	public final int getMoney() {
		return moneyIndicator.getMoney();
	}
	
	public final void setWeapon(Weapon weapon) {
		weaponIndicator.setWeapon(weapon);
	}
	
	public final Weapon getWeapon() {
		return weaponIndicator.getWeapon();
	}
	
	public final void setHealth(int health) {
		healthBar.setValue(health);
	}
	
	public final void updateInventory() {
		inventoryView.update();
	}
}
