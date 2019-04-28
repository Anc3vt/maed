package ru.ancevt.maed.gameobject;

public interface IDamaging extends ICollisioned {
	void setDamagingPower(final int damagingPower);
	int getDamagingPower();
	void setDamagingOwnerActor(Actor character);
	Actor getDamagingOwnerActor();
}
