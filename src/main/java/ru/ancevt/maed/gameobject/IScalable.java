package ru.ancevt.maed.gameobject;

public interface IScalable {
	void setScaleX(final float scale);
	void setScaleY(final float scale);
	void setScale(final float scaleX, final float scaleY);
	float getScaleX();
	float getScaleY();
}
