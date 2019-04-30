package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.gameobject.actionprogram.ActionProgram;

public interface IActioned extends IGameObject{
	void setActionProgram(ActionProgram actionProgram);
	ActionProgram getActionProgram();
	void actionProcess();
}
