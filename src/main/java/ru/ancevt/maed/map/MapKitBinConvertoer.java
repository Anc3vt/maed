package ru.ancevt.maed.map;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.ancevt.d2d2.io.Assets;
import ru.ancevt.maed.common.Path;

public class MapKitBinConvertoer {
	
	public static void main(String[] args) throws IOException {
		MapKitBinConvertoer c = new MapKitBinConvertoer();
		c.convert("mapkit1/");
	}

	private static final String COMMENT_CHAR = "#";
	
	public MapKitBinConvertoer()  {
		
		
	}
	
	public void convert(String assetDirectoryPath) throws IOException {
		final StringBuilder s = new StringBuilder();
		String line = null;

		final BufferedReader reader = Assets.readFile(Path.MAPKIT_DIRECTORY + assetDirectoryPath + "index.mapkit");
		
		final DataOutputStream r = new DataOutputStream(
			new FileOutputStream(Assets.getFile(Path.MAPKIT_DIRECTORY + assetDirectoryPath + "index.mapkit.bin"))
		);
		
		while((line = reader.readLine()) != null) {
			// skip comment and empty
			if (line.startsWith(COMMENT_CHAR) || line.trim().length() == 0) continue;

			s.append(line);
			
			if(line.trim().endsWith("\\")) continue;
			
			final String resultLine = s.toString().replaceAll("\\\\", "");
			
			final DataLine dataLine = DataLine.parse(resultLine);
			
			
		}
		
	}
	
	
	private static final String readString(DataInputStream r, int length) {
		final byte[] bytes = new byte[3];
		try {
			r.readFully(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(bytes);
	}
}








