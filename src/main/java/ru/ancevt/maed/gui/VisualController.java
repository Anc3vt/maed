package ru.ancevt.maed.gui;

import ru.ancevt.d2d2.common.D2D2;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.touch.TouchButton;
import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.common.Viewport;

public class VisualController extends DisplayObjectContainer {
	private Sprite spriteC, spriteB, spriteA;
	private Sprite spriteLeft, spriteRight;
	
	private TouchButton tbC, tbB, tbA, tbLeft, tbRight;
	
	private Controller controller;
	
	public VisualController(Controller controller) {
		setController(controller);
		
		spriteLeft = new Sprite("left");
		spriteRight = new Sprite("right");
		spriteC = new Sprite("c");
		spriteB = new Sprite("b");
		spriteA = new Sprite("a");

		tbC = new TouchButton() {
			@Override
			public boolean onTouchDown(int x, int y) {
				controller.setC(true);
				return super.onTouchDown(x, y);
			}
			
			@Override
			public boolean onTouchUp(int x, int y, boolean onArea) {
				controller.setC(false);
				return super.onTouchUp(x, y, onArea);
			}
		};
		tbC.setDisplayObject(spriteC);
		add(tbC);
		
		tbB = new TouchButton() {
			@Override
			public boolean onTouchDown(int x, int y) {
				controller.setB(true);
				return super.onTouchDown(x, y);
			}
			
			@Override
			public boolean onTouchUp(int x, int y, boolean onArea) {
				controller.setB(false);
				return super.onTouchUp(x, y, onArea);
			}
		};
		tbB.setDisplayObject(spriteB);
		add(tbB);
		
		tbA = new TouchButton() {
			@Override
			public boolean onTouchDown(int x, int y) {
				controller.setA(true);
				return super.onTouchDown(x, y);
			}
			
			@Override
			public boolean onTouchUp(int x, int y, boolean onArea) {
				controller.setA(false);
				return super.onTouchUp(x, y, onArea);
			}
		};
		tbA.setDisplayObject(spriteA);
		add(tbA);
		
		tbLeft = new TouchButton() {
			@Override
			public boolean onTouchDown(int x, int y) {
				controller.setLeft(true);
				return super.onTouchDown(x, y);
			}
			
			@Override
			public boolean onTouchUp(int x, int y, boolean onArea) {
				controller.setLeft(false);
				return super.onTouchUp(x, y, onArea);
			}
		};
		tbLeft.setDisplayObject(spriteLeft);
		add(tbLeft);

		tbRight = new TouchButton() {
			@Override
			public boolean onTouchDown(int x, int y) {
				controller.setRight(true);
				return super.onTouchDown(x, y);
			}
			
			@Override
			public boolean onTouchUp(int x, int y, boolean onArea) {
				controller.setRight(false);
				return super.onTouchUp(x, y, onArea);
			}
		};
		tbRight.setDisplayObject(spriteRight);
		add(tbRight);
		
		setEnabled(true);
		update();
	}
	
	public final void setEnabled(boolean value) {
		tbLeft.setEnabled(true);
		tbRight.setEnabled(true);
		tbC.setEnabled(true);
		tbB.setEnabled(true);
		tbA.setEnabled(true);
	}
	
	public final boolean isEnabled() {
		return tbLeft.isEnabled();
	}
	
	private final void update() {
		final int PADDING = 8;
		
		final int w = Viewport.WIDTH;
		final int h = Viewport.HEIGHT;

		final int y = (int) (h - tbLeft.getHeight() - PADDING);
		
		tbLeft.setXY(PADDING, y);
		tbRight.setXY(tbLeft.getX() + tbLeft.getWidth() + PADDING, y);
		
		tbA.setXY(w - tbA.getWidth() - PADDING, y);
		tbB.setXY(tbA.getX() - tbB.getWidth() - PADDING, y);
		tbC.setXY(tbB.getX() - tbC.getWidth() - PADDING , y);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
