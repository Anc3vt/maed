package ru.ancevt.maed.world;

import ru.ancevt.d2d2.display.DisplayObjectContainer;

public class WorldLayer extends DisplayObjectContainer {

	public final static int LAYER_COUNT = 10;
	
	private int index;
	
	public WorldLayer(final int index){
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
