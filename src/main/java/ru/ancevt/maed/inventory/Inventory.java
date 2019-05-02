package ru.ancevt.maed.inventory;

public class Inventory {
	
	private InventoryItem[] items;
	
	public Inventory(int size) {
		items = new InventoryItem[size];
	}
	
	public final void put(int slot, InventoryItem item) {
		items[slot] = item;
	}
	
	public final InventoryItem get(int slot) {
		return items[slot];
	}
	
	public final boolean isEmpty() {
		for(final InventoryItem i : items)
			if(i != null) return false;
		 
		return true;
	}
	
	public final boolean isFull() {
		for(final InventoryItem i : items)
			if(i == null) return false;
		
		return true;
	}
	
	public final int getFreeSlot() {
		for(int i = 0 ; i < items.length; i ++) {
			if(items[i] == null) return i;
		}
		return -1;
	}
	
	public final boolean put(InventoryItem item) {
		final int slot = getFreeSlot();
		if(slot != -1) {
			put(slot, item);
			return true;
		} else {
			return false;
		}
	}
}
