package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.gameobject.actorprogram.ActorProgram;

public interface IActioned extends IGameObject{
	void setActionProgram(ActorProgram actionProgram);
	ActorProgram getActionProgram();
	void actionProcess();
}
