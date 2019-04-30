package ru.ancevt.maed.gameobject.actionprogram;

import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.gameobject.Bot;

public class ActionProcessor {
	
	private ActionProgram actionProgram;
	private Controller controller;
	private Bot bot;
	
	public ActionProcessor(Bot bot) {
		actionProgram = new ActionProgram();
		this.bot = bot;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public void setActionProgram(ActionProgram actionProgram) {
		this.actionProgram = actionProgram;
	}
	
	public ActionProgram getActionProgram() {
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
					bot.go(bot.getDirection());
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




















