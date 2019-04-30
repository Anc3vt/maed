package ru.ancevt.maed.gameobject.actionprogram;

import ru.ancevt.maed.common.CommonPanel;
import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.gameobject.Actor_legacy;
import ru.ancevt.maed.gameobject.IDirectioned;

public class ActionProcessor {
	
	private ActionProgram actionProgram;
	private Controller controller;
	private Actor_legacy actor;
	
	public ActionProcessor(Actor_legacy directioned) {
		actionProgram = new ActionProgram();
		this.actor = directioned;
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
					actor.walk();
					break;
				case Action.STOP:
					
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
				
				case Action.PAUSE:
					pauseDelay = currentAction.getValue();
					break;
	
			default:
				break;
			}
		}
		CommonPanel.getInstance().setText("Text");
	
	}
}




















