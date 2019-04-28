package ru.ancevt.maed.editor;

import ru.ancevt.d2d2.common.BorderedRect;
import ru.ancevt.d2d2.display.Color;

public class SelectArea extends BorderedRect {
	
	private static final Color STROKE_COLOR = Color.GRAY;
	
	public SelectArea() {
		super(10f, 10f);
		setFillColor(null);
		
		setBorderColor(STROKE_COLOR);
	}
	
	@Override
	public void onEachFrame() {
		final float screenScaleX = getAbsoluteScaleX();
		setBorderWidth(1 / screenScaleX);
		
		super.onEachFrame();
	}
	
	public void setXY(SelectRectangle selectRectangle) {
		setXY(selectRectangle.getX1(), selectRectangle.getY1());
		setSize(selectRectangle.getWidth(), selectRectangle.getHeight());
	}
}
