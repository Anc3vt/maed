package ru.ancevt.maed.editor;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.common.DataKey;
import ru.ancevt.maed.gameobject.area.Area;
import ru.ancevt.maed.map.MapkitItem;

public class Cursor extends Sprite {

	private static final String DEFAULT_TEXTURE_REGION_NAME = "cursor";
	private static final float SCALE = 1.0f;

	private static Cursor instance;
	
	public static final Cursor getInstance() {
		return instance == null ? instance = new Cursor() : instance;
	}
	
	private Cursor() {
		super(DEFAULT_TEXTURE_REGION_NAME);
		setScale(SCALE, SCALE);
	}
	
	public final void setMapKitItem(final MapkitItem mapKitItem) {
		setColor(Color.WHITE);
		setVisible(true);
		
		if(mapKitItem == null) {
			setTexture(DEFAULT_TEXTURE_REGION_NAME);
			return;
		}
		
		if (mapKitItem.getCategory() == MapkitItem.Category.AREAS) {
			setTexture(DEFAULT_TEXTURE_REGION_NAME);

			final int areaType = mapKitItem.getDataLine().getInt(DataKey.AREA_TYPE);
			Color color;
			
			switch (areaType) {
			case Area.COLLISION:
				color = Color.GRAY;
				break;
			case Area.DAMAGING:
				color = Color.RED;
				break;
			case Area.CHECKPOINT:
				color = Color.GREEN;
				break;
			case Area.DOOR_TELEPORT:
				color = Color.DARK_GREEN;
				break;
				
			default:
				color = Color.WHITE;
				break;
			}
			
			setColor(color);
			
			return;
		} 

		setVisible(false);
	}
}
