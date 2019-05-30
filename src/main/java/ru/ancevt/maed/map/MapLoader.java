package ru.ancevt.maed.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.io.Assets;
import ru.ancevt.maed.common.DataKey;
import ru.ancevt.maed.common.Path;
import ru.ancevt.maed.gameobject.GameObjectFactory;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.music.Music;

public class MapLoader {

	public static final Map createEmptyMap(final String usingMapKitFile) {
		final Map map = new Map();

		try {
			final Mapkit mapKit = MapkitLoader.load(usingMapKitFile);
			map.setMapKit(mapKit);
		} catch (FileNotFoundException e) {
			System.err.printf(
				"Fatal error when creating map: not found map kit file \"%s\"",
				usingMapKitFile);
			e.printStackTrace();
			System.exit(1);
			
		}catch (IOException e) {
			System.err.printf(
				"Fatal error when creating map: i/o error when loading map kit \"%s\"",
				usingMapKitFile
			);
			System.exit(1);
		} 
		
		// Creating one empty room in new map
		map.addRoom(new Room(0));
		
		return map;
	}
	
	public static final Map load(final String assetFilePath) throws NumberFormatException, IOException {
		final Map map = new Map();
		
		Mapkit mapkit = null;
		Room room = null;
		
		final BufferedReader reader = Assets.readFile(Path.MAP_DIRECTORY + assetFilePath);
		String stringLine = null;
		while((stringLine = reader.readLine()) != null) {
			if(stringLine.startsWith("#") || stringLine.trim().length() == 0) continue;
			
			final DataLine dataLine = DataLine.parse(stringLine);
			if (dataLine == null)
				continue;
			
			if(dataLine.containsKey(DataKey.USING_MAPKIT)) {
				
				final String mapName = dataLine.getString(DataKey.NAME);
				
				map.setName(mapName);
				map.setGravity(dataLine.getFloat(DataKey.GRAVITY));
				
				final String usingMapKit = dataLine.getString(DataKey.USING_MAPKIT);
				
				try {
					mapkit = MapkitLoader.load(usingMapKit);
					map.setMapKit(mapkit);
					
				} catch (FileNotFoundException e) {
					System.err.printf(
						"Fatal error when loading map \"%s\": not found map kit file \"%s\"",
						mapName, usingMapKit);
					e.printStackTrace();
					System.exit(1);
					
				}	catch (IOException e) {
					System.err.printf(
						"Fatal error when loading map \"%s\": i/o error when loading map kit \"%s\"",
						mapName, usingMapKit
					);
					System.exit(1);
				} 
				
				if(dataLine.containsKey(DataKey.MUSIC)) {
					final String fileName = dataLine.getString(DataKey.MUSIC);
					final String mapKitDirectoryName = mapkit.getDirectory();
					
					final Music music = new Music(fileName, Path.MAPKIT_DIRECTORY + mapKitDirectoryName + fileName);
					map.setMusic(music);
				}
				
				continue;
			}
			
			if(dataLine.containsKey(DataKey.ROOM)) {
				final int roomId = dataLine.getInt(DataKey.ROOM);
				room = new Room(roomId);
				
				final Color backColor =
					new Color(
						Integer.parseInt(dataLine.getString(DataKey.BG_COLOR), 16)
					);
				
				final int roomWidth = dataLine.getInt(DataKey.ROOM_WIDTH);
				final int roomHeight = dataLine.getInt(DataKey.ROOM_HEIGHT);
				
				room.setSize(roomWidth, roomHeight);
				
				room.setBackColor(backColor);
				
				map.addRoom(room);
				continue;
			}
			
			final int mapkitItemId = dataLine.getInt(1);
			final MapkitItem mapkitItem = mapkit.getItemById(mapkitItemId);
			
			if(mapkitItem == null) {
				System.err.println("mapki id " +mapkitItemId + " not found in mapkit");
				continue;
			}
			
			final IGameObject gameObject 
				= GameObjectFactory.createGameObjectFromMapFile(mapkitItem, dataLine);
			room.addGameObject(gameObject, dataLine.getInt(2));
		}

		return map;
	}

}
