package ru.ancevt.maed.inventory;

import ru.ancevt.d2d2.display.texture.Texture;
import ru.ancevt.d2d2.display.texture.TextureManager;
import ru.ancevt.d2d2.exception.TextureException;

public class InventoryItemKey extends InventoryItem {
	
	private int keyTypeId;
	
	public InventoryItemKey(int keyTypeId) {
		this.keyTypeId = keyTypeId;
	}
	
	public final int getKeyTypeId() {
		return keyTypeId;
	}
	
	@Override
	public final Texture getTexture() {
		final TextureManager tm = TextureManager.getInstance();
		
		switch (keyTypeId) {
			case KeyType.RED:
				return tm.getTexture("p_key_red");
			case KeyType.GREEN:
				return tm.getTexture("p_key_green");
			case KeyType.BLUE:
				return tm.getTexture("p_key_blue");
			case KeyType.YELLOW:
				return tm.getTexture("p_key_yellow");
				
			default:
				throw new TextureException("unknown texture to key type " + keyTypeId);
		}
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + keyTypeId + "]";
	}
}
