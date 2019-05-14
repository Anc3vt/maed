package ru.ancevt.maed.gameobject.actorprogram;

import java.util.LinkedList;
import java.util.List;

public class ActorProgram {

	
	
	private int currentActionIndex;
	private List<Action> actions;
	
	public ActorProgram() {
		actions = new LinkedList<Action>();
	}
	
	public final boolean isEmpty() {
		return actions.isEmpty();
	}
	
	public final ActorProgram copy() {
		return ActorProgram.parse(stringify());
	}

	public static final ActorProgram parse(String source) {
		source = source.replaceAll("\\n", "");
		
		final ActorProgram actionProgram = new ActorProgram();
		
		final String[] splitted = source.split(";");
		for(int i = 0 ; i < splitted.length; i ++) {
			final Action action = new Action(splitted[i]);
			actionProgram.addAction(action);
		}
		
		return actionProgram;
	}
	
	public final String stringify() {
		final StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < getActionCount(); i ++) {
			stringBuilder.append(getAction(i).stringify());
			stringBuilder.append(';');
		}
		
		return stringBuilder.toString();
	}
	
	public int getActionCount() {
		return actions.size();
	}
	
	public void addAction(Action action) {
		actions.add(action);
	}
	
	public void removeAction(Action action) {
		actions.remove(action);
	}
	
	public Action getAction(int index) {
		return actions.get(index);
	}
	
	public final Action getNextAction() {
		final Action result = getAction(currentActionIndex);
		
		currentActionIndex++;
		if(currentActionIndex >= getActionCount()) {
			currentActionIndex = 0;
		}
		
		return result;
	}
	
	public final String toString() {
		return getClass().getSimpleName() + "[" + getActionCount() + "]";
	}
}















