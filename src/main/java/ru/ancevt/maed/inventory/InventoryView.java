package ru.ancevt.maed.inventory;

import ru.ancevt.d2d2.display.DisplayObjectContainer;

public class InventoryView extends DisplayObjectContainer {

	private Inventory inventory;
	private InventoryViewSlot[] slots;
	
	public InventoryView(Inventory inventory) {
		this.inventory = inventory;
		draw();
		update();
	}
	
	private final void draw() {
		slots = new InventoryViewSlot[inventory.size()];
		
		for(int i = 0; i < inventory.size(); i++) {
			final InventoryViewSlot vSlot = new InventoryViewSlot();
			add(vSlot, i * vSlot.getWidth(), 0);
			slots[i] = vSlot;
		}
	}
	
	public final void update() {
		for(int i = 0; i < slots.length; i ++) {
			final InventoryViewSlot slot = slots[i];
			slot.clear();
			final InventoryItem item = inventory.get(i);
			if(item != null) slot.setInventoryItem(item);
		}
	}
}
