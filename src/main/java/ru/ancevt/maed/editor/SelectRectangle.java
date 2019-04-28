package ru.ancevt.maed.editor;

public class SelectRectangle {
	private int x1, y1, x2, y2;
	private boolean pressed;

	public int getY2() {
		
		if(y2 < y1) {
			return y1;
		}
		
		return y2;
	}

	public void setY2(int height) {
		this.y2 = height;
	}

	public int getX2() {
		if(x2 < x1) {
			return x1;
		}
		
		return x2;
	}

	public void setX2(int width) {
		this.x2 = width;
	}

	public int getY1() {
		
		if(y2 < y1) {
			return y2;
		}
		
		return y1;
	}

	public void setY1(int y) {
		this.y1 = y;
	}

	public int getX1() {
		if(x2 < x1) {
			return x2;
		}
		return x1;
	}

	public void setX1(int x) {
		this.x1 = x;
	}
	
	public int getWidth() {
		return getX2() - getX1();
	}
	
	public int getHeight() {
		return getY2() - getY1();
	}
	
	public void setUp(int x1, int y1, int x2, int y2) {
		setX1(x1);
		setY1(y1);
		setX2(x2);
		setY2(y2);
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	@Override
	public String toString() {
		return "[SelectRectangle: " + getX1() + ", " + getY1() + ", " + getWidth() + ", " + getHeight()+ "]";
	}
}
