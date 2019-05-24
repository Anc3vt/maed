package ru.ancevt.maed.gameobject;

public interface ITight extends ICollisioned {
    void setFloorOnly(boolean b);
    boolean isFloorOnly();

    void setPushable(boolean b);
    boolean isPushable();

}
