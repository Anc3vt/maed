package ru.ancevt.maed.gameobject;

public interface IMoveable extends IGameObject {
	void setStartX(float x);
	void setStartY(float y);
	void setStartXY(float x,float y);
	void setSpeed(float speed);
	float getStartX();
	float getStartY();
	float getSpeed();
	float getMovingSpeedX();
	float getMovingSpeedY();
}
