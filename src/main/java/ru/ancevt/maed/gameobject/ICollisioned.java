package ru.ancevt.maed.gameobject;

public interface ICollisioned extends IGameObject{
	void setCollisionEnabled(boolean value);
    boolean isCollisionEnabled();
    void setCollisionArea(float x, float y, float w, float h);
    float getCollisionX();
    float getCollisionY();
    float getCollisionWidth();
    float getCollisionHeight();

    void setCollisionVisible(final boolean b);
    boolean isCollisionVisible();

    void onCollide(final ICollisioned collideWith);

}
