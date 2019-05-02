package ru.ancevt.maed.inventory;

import ru.ancevt.d2d2.display.DisplayObjectContainer;

public class InventoryView extends DisplayObjectContainer {

	private Inventory inventory;
	
	public InventoryView(Inventory inventory) {
		this.inventory = inventory;
	}
	
	private final void draw() {
	}
}
