package ru.ancevt.maed.inventory;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Sprite;

public class InventoryViewSlot extends DisplayObjectContainer{
	
	private final Sprite back;
	private Sprite sprite;
	
	public InventoryViewSlot() {
		back = new Sprite("inventory_slot");
		add(back);
		
		sprite = new Sprite();
		
		
		add(sprite);
	}
	
	public final void clear() {
		sprite.clearTexture();
	}
	
	public final void setInventoryItem(InventoryItem item) {
		if(item != null) {
			add(sprite);
			sprite.setTexture(item.getTexture());
		} else {
			sprite.removeFromParent();
		}
		
	}
}
