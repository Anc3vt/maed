package ru.ancevt.maed.gameobject.actorprogram;

import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.gameobject.Bot;

public class ActionProcessor {
	
	private ActorProgram actionProgram;
	private Controller controller;
	private Bot bot;
	
	public ActionProcessor(Bot bot) {
		actionProgram = new ActorProgram();
		this.bot = bot;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void setActionProgram(ActorProgram actionProgram) {
		this.actionProgram = actionProgram;
	}
	
	public ActorProgram getActionProgram() {
		return actionProgram;
	}
	
	private int pauseDelay;
	
	public void process() {
		if(pauseDelay > 0) {
			pauseDelay --;
			return;
		}
		
		if(!actionProgram.isEmpty()) {
			final Action currentAction = actionProgram.getNextAction();
			
			final String actionKey = currentAction.getActionKey();
			
			switch (actionKey) {
				case Action.WALK:
					
					if(bot.getDirection() > 0) {
						controller.setRight(true);
						controller.setLeft(false);
					} else {
						controller.setLeft(true);
						controller.setRight(false);
					}
					break;
				case Action.STOP:
					controller.setLeft(false);
					controller.setRight(false);
					break;
				case Action.LEFT:
					if(!bot.isFace2Face()) bot.go(-1);
					break;
				case Action.RIGHT:
					if(!bot.isFace2Face()) bot.go(1);
					break;
				case Action.ATTACK:
					controller.setB(true);
					break;
				case Action.UNARM:
					controller.setB(false);
					break;
				case Action.JUMP:
					controller.setA(true);
					break;
				case Action.P:
				case Action.PAUSE:
					pauseDelay = currentAction.getValue();
					break;
	
			default:
				break;
			}
		}
	
	}
}




















