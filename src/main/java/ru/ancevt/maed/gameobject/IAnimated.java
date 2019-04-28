package ru.ancevt.maed.gameobject;

public interface IAnimated extends IDirectioned, IGameObject {
	public int getAnimation();
	public void setAnimation(final int animationKey);
	public void setAnimation(final int animationKey, final boolean loop);
}
