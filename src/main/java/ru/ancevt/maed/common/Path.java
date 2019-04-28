package ru.ancevt.maed.common;

public class Path {
	public static final String MAPKIT_DIRECTORY = "mapkit/";
	public static final String MAP_DIRECTORY = "map/";
	
	public static final String getFileName(final String path) {
		final String[] s = path.split("/");
		return s[s.length-1];
	}
}
