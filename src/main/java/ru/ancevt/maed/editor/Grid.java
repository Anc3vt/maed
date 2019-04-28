package ru.ancevt.maed.editor;

import java.util.ArrayList;

import java.util.List;

import ru.ancevt.d2d2.common.D2D2;
import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.IColored;

public class Grid extends DisplayObjectContainer implements IColored {
	
	public static final int SIZE = 16;
	
	private static final float ALPHA = 0.075f;
	public static final int DEFAULT_COLOR = 0xFFFFFF;
	
	private List<Line> lines;
	private Color color;
	
	public Grid() {
		lines = new ArrayList<Line>();
		setColor(DEFAULT_COLOR);
	}
	
	public final void redrawLines() {
		recreate(color);
	}
	
	private final void recreate(Color color) {
		removeAllChildren();
		
		final int w = D2D2.getRenderer().getWidth();
		final int h = D2D2.getRenderer().getHeight();
		
		for(float x = 0; x < w; x += SIZE) {
			final Line line = new Line(Line.VERTICAL);
			line.setColor(color);
			line.setX(x);
			line.setAlpha(ALPHA);
			add(line);
			lines.add(line);
		}
		
		for(float y = 0; y < h; y += SIZE) {
			final Line line = new Line(Line.HORIZONTAL);
			line.setColor(color);
			line.setY(y);
			line.setAlpha(ALPHA);
			add(line);
			lines.add(line);
		}
	}
	
	@Override
	public void onEachFrame() {
		while(getAbsoluteX() < -16) moveX(16);
		while(getAbsoluteY() < -16) moveY(16);
		while(getAbsoluteX() > 0) moveX(-16);
		while(getAbsoluteY() > 0) moveY(-16);
		super.onEachFrame();
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
		recreate(color);
	}

	@Override
	public Color getColor() {
		return lines.get(0).getColor();
	}

	@Override
	public void setColor(int rgb) {
		setColor(new Color(rgb));
	}
}

class Line extends PlainRect {
	
	public static final byte HORIZONTAL = 0x00;
	public static final byte VERTICAL = 0x01;
	
	private byte orientation;
	
	public Line(final byte orientation) {
		super(1.0f, 1.0f);
		this.setOrientation(orientation);
	}

	public byte getOrientation() {
		return orientation;
	}

	private void setOrientation(byte orientation) {
		this.orientation = orientation;
		
		final int w = D2D2.getRenderer().getWidth();
		final int h = D2D2.getRenderer().getHeight();
		
		switch (orientation) {
			case HORIZONTAL:
				setScale(w, 1.0f);
				break;
				
			case VERTICAL:
				setScale(1.0f, h);
				break;
		}		
	}
	
	@Override
	public void onEachFrame() {
		final DisplayObjectContainer parent = getParent();

		if(parent == null) return;
		
		switch (orientation) {
			case HORIZONTAL:
				
				final float screenScaleY = parent.getAbsoluteScaleY();
				setScaleY(1.0f / screenScaleY);
				
				break;
				
			case VERTICAL:
				
				final float screenScaleX = parent.getAbsoluteScaleX();
				setScaleX(1.0f / screenScaleX);
			
				break;

		default:
			break;
		}
		
		super.onEachFrame();
	}
}