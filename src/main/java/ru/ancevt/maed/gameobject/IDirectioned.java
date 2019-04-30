package ru.ancevt.maed.gameobject;

public interface IDirectioned {
	void setDirection(final int direction);
	int getDirection();
	void setStartDirection(int dir);
	int getStartDirection();
}
