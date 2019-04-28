package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.gameobject.area.AreaHook;

public interface IHookable extends IGravitied, ICollisioned {
	void setHook(final AreaHook hook);
	AreaHook getHook();
}
