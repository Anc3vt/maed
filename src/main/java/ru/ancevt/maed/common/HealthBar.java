package ru.ancevt.maed.common;

import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;

public class HealthBar extends DisplayObjectContainer {
	private static final int WIDTH = 50;
	private static final int HEIGHT = 8;
	
	private static final int DEFAULT_MAX = 50;
	private static final int DEFAULT_VALUE = 50;
	
	private PlainRect back;
	private PlainRect fore;
	private float max;
	private float value;
	
	public HealthBar() {
		back = new PlainRect(WIDTH, HEIGHT, Color.DARK_RED);
		fore = new PlainRect(WIDTH, HEIGHT, Color.RED);

		add(back);
		add(fore);
		
		setMax(DEFAULT_MAX);
		setValue(DEFAULT_VALUE);
	}
	
	public final void setMax(float value) {
		this.max = value;
		redraw();
	}
	
	public final float getMax() {
		return max;
	}
	
	public final void setValue(float value) {
		if(value < 0) value = 0; else
		if(value > max) value = max;
		this.value = value;
		redraw();
	}
	
	public final float getValue() {
		return value;
	}
	
	public void setWidth(float value) {
		back.setWidth(value);
		redraw();
	}
	
	public void setHeight(float value) {
		back.setHeight(value);
		fore.setHeight(value);
	}
	
	private final void redraw() {
		final float p = value / max;
		fore.setWidth(back.getWidth() * p);
	}
	
	
	
	
	
	
	
	
	
	
}
