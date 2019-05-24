package ru.ancevt.maed.gui;

import java.io.IOException;

import ru.ancevt.d2d2.display.text.BitmapFont;

public class Font {
	
	private static BitmapFont gameFont;
	public static final BitmapFont getGameFont() {
		try {
			return gameFont == null ? gameFont = BitmapFont.loadBitmapFont("Menlo-Regular.bmf") : gameFont;
		} catch (IOException e) {
			e.printStackTrace();
			return BitmapFont.getDefaultBitmapFont();
		}
	}
}
