package ru.ancevt.maed.common;

abstract public class Controller {

	private boolean up, down, left, right, a, b, c;
	private boolean enabled;
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		up = down = left = right = a = b = c = false;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isC() {
		return c;
	}

	public void setC(boolean c) {
		if(!enabled) return;
		this.c = c;
	}

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		if(!enabled) return;
		this.b = b;
	}

	public boolean isA() {
		return a;
	}

	public void setA(boolean a) {
		if(!enabled) return;
		this.a = a;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		if(!enabled) return;
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		if(!enabled) return;
		this.left = left;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		if(!enabled) return;
		this.down = down;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		if(!enabled) return;
		this.up = up;
	}
	
	public void reset() {
		setC(false);
		setB(false);
		setA(false);
		setLeft(false);
		setRight(false);
		setDown(false);
		setUp(false);
	}
	
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Controller[");
		
		sb.append(
			String.format(
				"%c%c %c%c%c",
				isLeft()  ? '*' : '.',
				isRight() ? '*' : '.',
				isC()     ? 'C' : 'c',
				isB()     ? 'B' : 'b',
				isA()     ? 'A' : 'a'
			)
		);
		
		sb.append("]");
		return sb.toString();
	}
}
