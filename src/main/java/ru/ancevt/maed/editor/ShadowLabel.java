package ru.ancevt.maed.editor;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.text.BitmapText;

public class ShadowLabel extends DisplayObjectContainer {
	
	private BitmapText bitmapText;
	private BitmapText shadow;
	
	public ShadowLabel() {
		bitmapText = new BitmapText();
		shadow = new BitmapText();
		
		shadow.setColor(Color.BLACK);
		
		add(shadow, 1, 1);
		add(bitmapText);
	}
	
	public ShadowLabel(String text) {
		this();
		setText(text);
	}
	
	public void setText(String text) {
		bitmapText.setText(text);
		shadow.setText(text);
	}
	
	public void setBounds(float w, float h) {
		bitmapText.setBounds(w, h);
		shadow.setBounds(w, h);
	}
	
	public void setBoundWidth(float w) {
		bitmapText.setBoundWidth(w);
		shadow.setBoundWidth(w);
	}
	
	public void setBoundHeight(float h) {
		bitmapText.setBoundHeight(h);
		shadow.setBoundHeight(h);
	}
	
	public float getBoundWidth() {
		return bitmapText.getBoundWidth();
	}
	
	public float getBoundHeight() {
		return bitmapText.getBoundHeight();
	}
	
	public int getTextWidth() {
		return bitmapText.getTextWidth();
	}
	
	public int getTextHeight() {
		return bitmapText.getTextHeight();
	}
	
	public String getText() {
		return bitmapText.getText();
	}
	
	public void setColor(Color color) {
		bitmapText.setColor(color);
	}
	
	public Color getColor() {
		return bitmapText.getColor();
	}

}
