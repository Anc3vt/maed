package ru.ancevt.maed.gameobject;

public interface IGravitied extends IMoveable {
	float getWeight();
	void setWeight(final float weight);
	void setFloor(final ICollisioned floor);
	ICollisioned getFloor();
	void toVelocityX(float value);
	void toVelocityY(float value);
	void setVelocityX(float velocityX);
	void setVelocityY(float velocityY);
	void setVelocity(float vX, float vY);
	float getVelocityX();
	float getVelocityY();
	
	void setGravityEnabled(boolean b);
	boolean isGravityEnabled();
}
