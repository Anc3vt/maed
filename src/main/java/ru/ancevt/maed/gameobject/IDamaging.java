package ru.ancevt.maed.gameobject;

public interface IDamaging extends ICollisioned {
	void setDamagingPower(final int damagingPower);
	int getDamagingPower();
	void setDamagingOwnerActor(Actor_legacy character);
	Actor_legacy getDamagingOwnerActor();
}
