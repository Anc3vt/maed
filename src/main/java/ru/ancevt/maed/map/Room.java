package ru.ancevt.maed.map;

import java.util.ArrayList;
import java.util.List;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.area.AreaCheckpoint;
import ru.ancevt.maed.world.WorldLayer;

public class Room {
	
	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 512;
	
	public static final Color DEFAULT_BACKROUND_COLOR = Color.BLACK;
	
	private int id;
	private Color backColor;
	private int width, height;
	private List<IGameObject>[] gameObjects;
	
	@SuppressWarnings("unchecked")
	public Room(final int id) {
		this.id = id;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		
		setBackColor(DEFAULT_BACKROUND_COLOR);
		
		gameObjects = new List[WorldLayer.LAYER_COUNT];
		
		for(int i = 0; i < WorldLayer.LAYER_COUNT; i ++) {
			gameObjects[i] = new ArrayList<IGameObject>();
		}
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + gameObjects.length + "]";
	}
	
	public int getId() {
		return id;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setSize(final int w, final int h) {
		setWidth(w);
		setHeight(h);
	}
	
	public final AreaCheckpoint getCheckpointById(int id) {
		for(int l = 0 ; l < gameObjects.length; l ++) {
			for(int i = 0; i < gameObjects[l].size(); i ++) {
				final IGameObject o = gameObjects[l].get(i);
				if(o instanceof AreaCheckpoint) {
					if(id == ((AreaCheckpoint)o).getCheckpointId()) return (AreaCheckpoint)o;
				}
			}
		}
		return null;
	}
	
	public final void addGameObject(final IGameObject gameObject, final int layer) {
		gameObjects[layer].add(gameObject);
	}
	
	public final void removeGameObject(final IGameObject gameObject, final int layer) {
		gameObjects[layer].remove(gameObject);
	}
	
	public final int getGameObjectsCount(final int layer) {
		return gameObjects[layer].size();
	}
	
	public final IGameObject getGameObject(final int layer, final int index) {
		return gameObjects[layer].get(index);
	}
}
