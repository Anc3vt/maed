package ru.ancevt.maed.world;

import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.Room;

public interface WorldListener {
	void onWorldMapChange(final Map map);
	void onWorldRoomChange(final Room room);
}
