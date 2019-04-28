package ru.ancevt.maed.gameobject;

public interface IRotatable extends IGameObject {
	void setRotation(final float r);
	void rotate(final float r);
	float getRotation();
}
