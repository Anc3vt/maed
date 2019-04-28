package ru.ancevt.maed.common;

public class TilesetZone {
	private int x, y, width, height;

	public TilesetZone(String plainData) {
		final String[] splitted = plainData.split(",");

		final int x = Integer.parseInt(splitted[0].trim());
		final int y = Integer.parseInt(splitted[1].trim());
		final int w = Integer.parseInt(splitted[2].trim());
		final int h = Integer.parseInt(splitted[3].trim());
		
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
	}
	
	public TilesetZone(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + x + ", " + y + ", " + width + ", " + height + "]";
	}
}
