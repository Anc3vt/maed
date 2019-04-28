package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.common.Controller;

public interface IControllable {
	void setController(final Controller controller);
	Controller getController();
}
