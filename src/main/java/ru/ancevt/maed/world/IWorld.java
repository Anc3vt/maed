package ru.ancevt.maed.world;

import ru.ancevt.d2d2.display.IDisplayObject;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.map.Map;

public interface IWorld extends IDisplayObject {
	
	int getGameObjectsCount();
	IGameObject getGameObject(int index);
	
	void addGameObject(IGameObject gameObject, int layer, boolean updateRoom);
	void removeGameObject(IGameObject gameObject, boolean updateRoom);
	
	Map getMap();
	void setMap(Map map);
	
	void switchRoom(int roomId, float actorX, float actorY);
	boolean isSwitchingRoomsNow();
	
	WorldLayer getLayer(int layerNumber);
	
	void setPlayMode(boolean value);
	boolean isPlayMode();
}
