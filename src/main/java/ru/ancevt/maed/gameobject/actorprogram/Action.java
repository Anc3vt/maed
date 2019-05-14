package ru.ancevt.maed.gameobject.actorprogram;

public class Action {
	public final static String WALK = "walk";
	public final static String LEFT = "left";
	public final static String RIGHT = "right";
	public final static String UNARM = "unarm";
	public final static String STOP = "stop";
	public final static String PAUSE = "pause";
	public final static String P = "p";
	public final static String JUMP = "jump";
	public final static String ATTACK = "attack";
		
	private String action;
	private int value;
	private boolean hasValue;
	
	public Action(String action) {
		action=action.trim();
		
		final String[] splitted = action.split(" ");
		
		this.action = splitted[0];
		
		if(splitted.length > 1) {
			value = Integer.valueOf(splitted[1]);
			hasValue = true;
		}
	}
	
	public String stringify() {
		return action + (hasValue ? " " + value : "");
	}
	
	public String getActionKey() {
		return action;
	}
	
	public int getValue() {
		return value;
	}
}
