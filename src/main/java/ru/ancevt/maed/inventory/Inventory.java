package ru.ancevt.maed.inventory;

public class Inventory {
	
	private InventoryItem[] slots;
	
	public Inventory(int size) {
		slots = new InventoryItem[size];
	}
	
	public final int size() {
		return slots.length;
	}
	
	public final void put(int slot, InventoryItem item) {
		slots[slot] = item;
	}
	
	public final InventoryItem get(int slot) {
		return slots[slot];
	}
	
	public final boolean isEmpty() {
		for(final InventoryItem i : slots)
			if(i != null) return false;
		 
		return true;
	}
	
	public final boolean isFull() {
		for(final InventoryItem i : slots)
			if(i == null) return false;
		
		return true;
	}
	
	public final int getFreeSlot() {
		for(int i = 0 ; i < slots.length; i ++) {
			if(slots[i] == null) return i;
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
	
	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder(getClass().getSimpleName() + "[цц");
		
		for(int i = 0; i < slots.length; i ++) {
			s.append("\n Slot" + i + ": " + slots[i]);
		}
		
		return s.toString() + "\n]";
		
	}
	
	public final int keySlot(int keyTypeId) {
		for(int i = 0; i < slots.length; i ++) {
			final InventoryItem item = slots[i];
			if(item == null) continue;
			if(item instanceof InventoryItemKey) {
				final InventoryItemKey k = (InventoryItemKey)item;
				if(keyTypeId == k.getKeyTypeId()) return i;
			}
		}
		
		return -1;
	}

	public void clearSlot(int index) {
		slots[index] = null; 
	}
}



















