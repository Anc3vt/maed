package ru.ancevt.maed.map;

import java.util.ArrayList;
import java.util.List;

import ru.ancevt.d2d2.display.texture.TextureAtlas;
import ru.ancevt.d2d2.display.texture.TextureManager;
import ru.ancevt.maed.common.Path;

public class Mapkit {
	
	private String name;
	private List<MapkitItem> items;
	private TextureAtlas textureAtlas;
	private String assetDirectoryPath;

	public Mapkit() {
		items = new ArrayList<MapkitItem>();
	}
	
	public Mapkit(String assetDirectoryPath) {
		this();
		this.assetDirectoryPath = assetDirectoryPath;
	}
	
	public final String getDirectory() {
		return assetDirectoryPath;
	}
	
	public MapkitItem getItemById(int itemId) {
		
		for(final MapkitItem item : items) {
			if(item.getId() == itemId) return item;
		}

		throw new MapkitException("No such mapkit item with id \"" + itemId + "\" in mapkit \"" + getName() + "\"");
	}
	
	public MapkitItem getItemByName(String name) {
		for(final MapkitItem item : items) {
			if(name.equals(item.getName())) return item;
		}

		throw new MapkitException("No such mapkit item with name \"" + name + "\" in mapkit \"" + getName() + "\"");
	}
	
	public void addItem(MapkitItem item) {
		items.add(item);
	}
	
	public void removeItem(MapkitItem item) {
		items.remove(item);
	}
	
	public int getItemCount() {
		return items.size();
	}
	
	public MapkitItem getItem(int index) {
		return items.get(index);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public final void setTextureAtlas(final TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

	public TextureAtlas getTextureAtlas() {
		if(textureAtlas == null) {
			textureAtlas = TextureManager.getInstance().loadTextureAtlas(Path.MAPKIT_DIRECTORY + assetDirectoryPath + "tileset.png");
		}
		return textureAtlas;
	}

	public MapkitItem createItem(String dataLineSource) {
		return createItem(DataLine.parse(dataLineSource));
	}
	
	public MapkitItem createItem(DataLine dataLine) {
		final MapkitItem item = new MapkitItem(this, dataLine);
		addItem(item);
		return item;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + 
			"[name: " + name + 
			", items: " + getItemCount() +
			"]";
	}
}
