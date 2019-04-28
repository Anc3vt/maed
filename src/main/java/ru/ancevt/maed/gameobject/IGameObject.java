package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.display.IDisplayObject;
import ru.ancevt.maed.map.MapkitItem;

public interface IGameObject extends IDisplayObject {
	int getId();
    boolean isSaveable();
    MapkitItem getMapkitItem();
    IGameObject copy();
}
