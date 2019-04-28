package ru.ancevt.maed.map;

import java.util.ArrayList;
import java.util.List;

import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.music.Music;
import ru.ancevt.maed.world.WorldLayer;

public class Map {
	private static final int MAX_GAME_OBJECTS = 2147483647;
	
	public static final int INVALID_GAME_OBJECT_ID = -1;
	
	public static final float DEFAULT_GRAVITY = 1.0f;
	public static final String DEFAULT_NAME = "unnamed map";
	
	private final List<Room> rooms;
	
	private String name;
	private float gravity;
	private Mapkit mapkit;
	private Music music;
	
	private static Map currentMap;
	
	public Map() {
		currentMap = this;
		rooms = new ArrayList<Room>();
		setName(DEFAULT_NAME);
		setGravity(DEFAULT_GRAVITY);
	}
	
	public final void addRoom(final Room room) {
		rooms.add(room);
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("Map[%s, rooms: %d, gravity: %f]", getName(), rooms.size(), getGravity());
	}

	public Mapkit getMapkit() {
		return mapkit;
	}

	public void setMapKit(Mapkit mapkit) {
		this.mapkit = mapkit;
	}

	public final int getRoomsCount() {
		return rooms.size();
	}
	
	public final Room getRoomByIndex(final int index) {
		return rooms.get(index);
	}
	
	public final Room getRoomById(final int roomId) {
		for(int i = 0; i < rooms.size(); i ++) {
			final Room room = rooms.get(i);
			if(room.getId() == roomId)
				return room;
		}
		
		return null;
	}
	
	public final Room createRoom() {
		int newRoomId = 0;
		while(getRoomById(newRoomId) != null) {
			newRoomId++;
		}
		
		final Room room = new Room(newRoomId);
		addRoom(room);
		
		return room;
	}
	
	public final IGameObject getGameObjectById(final int gameObjectId) {
		for(int r = 0; r < rooms.size(); r++ ) {

			final Room room = getRoomByIndex(r);
			for(int l = 0; l < WorldLayer.LAYER_COUNT; l ++) {
				
				for(int i = 0; i < room.getGameObjectsCount(l); i ++) {
					final IGameObject gameObject = room.getGameObject(l, i);
					if(gameObject.getId() == gameObjectId)
						return gameObject;
				}
				
			}
		}
		return null;
	}
	
	public final int getNextFreeGameObjectId() {
		for(int i = 1; i < MAX_GAME_OBJECTS; i ++) {
			final IGameObject gameObject = getGameObjectById(i);
			if(gameObject == null) return i;
		}
		return INVALID_GAME_OBJECT_ID;
	}

	public static Map getCurrentMap() {
		return currentMap;
	}

	public static void setCurrentMap(Map currentMap) {
		Map.currentMap = currentMap;
	}
	
	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
	
}



























