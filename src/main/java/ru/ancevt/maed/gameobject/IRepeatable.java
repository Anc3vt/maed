package ru.ancevt.maed.gameobject;

public interface IRepeatable {
	void setRepeat(int repeatX, int repeatY);
	void setRepeatX(int repeatX);
	void setRepeatY(int repeatY);
	int getRepeatX();
	int getRepeatY();
	float getOriginalWidth();
	float getOriginalHeight();
}
