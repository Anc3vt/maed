package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.gameobject.action.ActionProgram;

public interface IActioned extends IGameObject{
	void setActionProgram(ActionProgram actionProgram);
	ActionProgram getActionProgram();
}
