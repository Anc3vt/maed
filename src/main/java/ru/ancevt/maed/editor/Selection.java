package ru.ancevt.maed.editor;

import ru.ancevt.d2d2.common.BorderedRect;
import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObject;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.IRepeatable;
import ru.ancevt.maed.gameobject.area.Area;

public class Selection extends DisplayObjectContainer {
	private final BorderedRect rect;
	
	private final IGameObject gameObject;
	private RepeatControl repeatControl;
	
	public Selection(final IGameObject gameObject) {
		this.gameObject = gameObject;
		rect = new BorderedRect(1, 1);
		rect.setFillColor(Color.BLUE);
		rect.setBorderColor(Color.BLACK);
		rect.setAlpha(0.25f);
		
		final DisplayObject displayObject = (DisplayObject)gameObject;
		setSize(displayObject.getWidth(), displayObject.getHeight());
		
		add(rect);
		
		if(gameObject instanceof IRepeatable || gameObject instanceof Area) {
			repeatControl = new RepeatControl();
			add(repeatControl, getWidth()-8, getHeight()-8);
		}
	}
	
	public final void setWidth(final float width) {
		rect.setWidth(width);
	}
	
	public final void setHeight(final float height) {
		rect.setHeight(height);
	}
	
	@Override
	public float getWidth() {
		return rect.getWidth();
	}
	
	@Override
	public float getHeight() {
		return rect.getHeight();
	}
	
	public final void setSize(final float w, final float h) {
		setWidth(w);
		setHeight(h);
	}

	public final IGameObject getGameObject() {
		return gameObject;
	}
	
	@Override
	public void onEachFrame() {
		setXY(
			gameObject.getX(),
			gameObject.getY()
		);
		
		setSize(
			gameObject.getWidth(),
			gameObject.getHeight()
		);
		
		if(repeatControl != null && repeatControl.hasParent()) {
			repeatControl.setXY(getWidth()-8, getHeight()-8);
//			final float parentScale = getParent().getAbsoluteScaleX();
//			repeatControl.setScale(1f / parentScale, 1f / parentScale);
		}
		
		
		
		super.onEachFrame();
	}
}

class RepeatControl extends DisplayObjectContainer {
	
	private final PlainRect rect, shadow;
	
	public RepeatControl() {
		shadow = new PlainRect(10,10);
		shadow.setColor(Color.BLACK);
		
		add(shadow, -1, -1);
		
		rect = new PlainRect(8,8);
		rect.setColor(Color.WHITE);
		add(rect);
		
	}
	
}
