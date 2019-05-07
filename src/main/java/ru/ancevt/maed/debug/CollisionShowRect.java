package ru.ancevt.maed.debug;

import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.gameobject.ICollisioned;

public class CollisionShowRect extends PlainRect {
	
	private ICollisioned collisioned;
	
	public CollisionShowRect(ICollisioned collisioned) {
		this.collisioned = collisioned;
		setColor(Color.DARK_GREEN);
		update();
	}
	
	public void update() {
		setXY(collisioned.getCollisionX(), collisioned.getCollisionY());
		setSize(collisioned.getCollisionWidth(), collisioned.getCollisionHeight());
	}
	
	@Override
	public void onEachFrame() {
		update();
		super.onEachFrame();
	}
}
