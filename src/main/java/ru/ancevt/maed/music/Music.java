package ru.ancevt.maed.music;

import ru.ancevt.d2d2.sound.Sound;

public class Music extends Sound {
	
	private String name;
	
	public Music(String name, String assetFilePath) {
		super(assetFilePath);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
