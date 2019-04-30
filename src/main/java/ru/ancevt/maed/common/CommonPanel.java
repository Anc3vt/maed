package ru.ancevt.maed.common;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.text.BitmapText;

public class CommonPanel extends DisplayObjectContainer {
	
	private static CommonPanel instance;
	public static CommonPanel getInstance() {
		return instance == null ? new CommonPanel() : instance;
	}
	
	
	private BitmapText bitmapText;
	
	private CommonPanel() {
		bitmapText = new BitmapText();
		bitmapText.setText("Hello world!");
		bitmapText.setColor(Color.BLUE);
		bitmapText.setScale(2f, 2f);
		add(bitmapText);
	}
	
	public final void setText(String text) {
		bitmapText.setText(text);
	}
}
