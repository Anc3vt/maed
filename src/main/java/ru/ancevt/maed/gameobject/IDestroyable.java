package ru.ancevt.maed.gameobject;

public interface IDestroyable extends ICollisioned {
	void setMaxHealth(final int health);
	int getMaxHealth();
	void setHealth(final int health);
	int getHealth();
	void addHealth(final int toHealth);
	void onDamage(IDamaging damageFrom);
	void setUnattainable(boolean value);
	boolean isUnattainable();
}
