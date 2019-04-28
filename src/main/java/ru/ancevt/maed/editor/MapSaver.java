package ru.ancevt.maed.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import ru.ancevt.maed.gameobject.GameObjectFactory;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.map.DataLine;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.Room;
import ru.ancevt.maed.world.WorldLayer;

public class MapSaver {
	public static void save(Map map, File file) throws FileNotFoundException {
		
		final PrintWriter printWriter = new PrintWriter(file);
		
		// name = Test map | gravity = 1.000000 | using-mapkit = mapkit1/ | music = lvl0.mp3

		printWriter.printf(
			"name = %s | gravity = %f | using-mapkit = %s ",
			map.getName(),
			map.getGravity(),
			map.getMapkit().getDirectory()
		);
		
		if(map.getMusic() != null) {
			printWriter.printf("| music = %s ", map.getMusic().getName());
		}
		
		printWriter.println();
		printWriter.println();

		// room = 1 | room-width = 1600 | room-height = 240 | bg-color = 66aa
		for(int i = 0; i < map.getRoomsCount(); i ++) {
			final Room room = map.getRoomByIndex(i);
			
			printWriter.printf(
				"room = %d | room-width = %d | room-height = %d | bg-color = %s\n",
				room.getId(),
				room.getWidth(),
				room.getHeight(),
				room.getBackColor().toHexString()
			);
			
			System.out.printf(
				"room = %d | room-width = %d | room-height = %d | bg-color = %s\n",
				room.getId(),
				room.getWidth(),
				room.getHeight(),
				room.getBackColor().toHexString()
			);
			
			for(int layer = 0; layer < WorldLayer.LAYER_COUNT; layer ++) {
				for(int j = 0; j < room.getGameObjectsCount(layer); j ++) {
					final IGameObject o = room.getGameObject(layer, j);
					final DataLine dataLine = DataLine.parse(GameObjectFactory.gameObjectToDataLineString(o, layer));
					printWriter.println(dataLine.stringify());
					System.out.println(dataLine.stringify());
				}
			}
			
			printWriter.println();
		}
		
		printWriter.close();
	}
}



















