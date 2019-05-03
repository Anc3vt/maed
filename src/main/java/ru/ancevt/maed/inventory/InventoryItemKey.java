package ru.ancevt.maed.inventory;

import ru.ancevt.d2d2.display.texture.Texture;
import ru.ancevt.d2d2.display.texture.TextureManager;

public class InventoryItemKey extends InventoryItem {
	
	private int keyType;
	
	public InventoryItemKey(int keyType) {
		this.keyType = keyType;
	}
	
	@Override
	public final Texture getTexture() {
		final TextureManager tm = TextureManager.getInstance();
		
		switch (keyType) {
			case KeyType.RED:
				tm.getTexture("p_key_red");
				break;
			case KeyType.GREEN:
				tm.getTexture("p_key_green");
				break;
			case KeyType.BLUE:
				tm.getTexture("p_key_blue");
				break;
			case KeyType.YELLOW:
				tm.getTexture("p_key_yellow");
				break;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + keyType + "]";
	}
}
