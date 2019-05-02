package ru.ancevt.maed.inventory;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Sprite;

public class InventoryViewSlot extends DisplayObjectContainer{
	
	private final Sprite back;
	
	public InventoryViewSlot() {
		back = new Sprite("inventory_slot");
		add(back);
	}
	
	public void put(Inventory item) {
		
	}
}
