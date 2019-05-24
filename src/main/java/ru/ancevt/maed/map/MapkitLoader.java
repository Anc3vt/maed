package ru.ancevt.maed.map;

import java.io.BufferedReader;
import java.io.IOException;

import ru.ancevt.d2d2.io.Assets;
import ru.ancevt.maed.common.Path;

public class MapkitLoader {
	
//	public static void main(String[] args) throws IOException {
//		final Mapkit mapkit = MapkitLoader.load("mapkit1");
//		System.out.println(mapkit.toString());
//		
//		final MapkitItem item = mapkit.getItemById(20010);
//		System.out.println(item.toString());
//		
//	}
	
	private static final String COMMENT_CHAR = "#";
	
	private static final String PROP_NAME = "name";
	
	private static final String PROP_DEF = "def";
	
	public static final Mapkit load(String assetDirectoryPath) throws IOException {
		if(!assetDirectoryPath.endsWith("/")) 
			assetDirectoryPath += "/";
		
		final Mapkit mapkit = new Mapkit(assetDirectoryPath);
		
		new DefaultMapkitItemFiller(mapkit).fill();
        
		final BufferedReader reader = Assets.readFile(Path.MAPKIT_DIRECTORY + assetDirectoryPath + "index.mapkit");

		final StringBuilder s = new StringBuilder();
		String line = null;
		
		while((line = reader.readLine()) != null) {
			// skip comment and empty
			if (line.startsWith(COMMENT_CHAR) || line.trim().length() == 0) continue;

			s.append(line);
			
			if(line.trim().endsWith("\\")) continue;
			
			final String resultLine = s.toString().replaceAll("\\\\", "");
			
			validateResultDataLine(resultLine);
			
			final DataLine dataLine = DataLine.parse(resultLine);
			if(dataLine.size() == 0) continue;
			
			s.setLength(0);
			
			if(PROP_DEF.equals(dataLine.getString(0))) {
				final String name = dataLine.getString(PROP_NAME);
				mapkit.setName(name);
				continue;
			}
			mapkit.addItem(createMapKitItem(mapkit, dataLine));
		}
		
		return mapkit;
	}

	private static final MapkitItem createMapKitItem(final Mapkit mapkit, final DataLine dataLine) {
		final MapkitItem result = new MapkitItem(mapkit, dataLine);
		return result;
	}
	
	private static final void validateResultDataLine(String dataLineSource) {
		if(dataLineSource.startsWith("def")) return;
		
		final String[] s= dataLineSource.split("||");
		
		try {
			Integer.parseInt(s[0]);
			Integer.parseInt(s[1]);
			
		} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("imvalid mapkit item: " + dataLineSource);
		}
		
	}
	
}











