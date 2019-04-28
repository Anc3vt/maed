package ru.ancevt.maed.gameobject.action;

import java.util.ArrayList;
import java.util.List;

public class ActionProgram {
	
	private static final String DELIMITER = ";";
	
	private List<ActionStatement> statements;
	private ActionStatement currentStatement;
	private int currentIndex;
	private int pauseDelay;
	private boolean ended;
	private boolean moving, walking;
	private float speedX, speedY;
	
	private int loopBeginIndex;
	
	public ActionProgram() {
		statements = new ArrayList<ActionStatement>();
	}
	
	public ActionProgram(final String program) {
		this();
		parse(program);
	}
	
	private final void parse(final String program) {
		final String[] s = program.split(DELIMITER);
		for(int i = 0; i < s.length; i ++) {
			final String current = s[i].trim();
			if(current == null || current.isEmpty()) continue;
			final ActionStatement as = new ActionStatement(current);
			statements.add(as);
			
			if(as.getKey() == ActionStatement.BEGIN) {
				loopBeginIndex = i;
			}
		}
	}
	
	public final void reset() {
		pauseDelay = 0;
		currentIndex = 0;
		if(!statements.isEmpty()) currentStatement = statements.get(0);
		ended = false;
		walking = false;
		setMoving(false);
		setSpeedX(0.0f);
		setSpeedY(0.0f);
	}
	
	public final void goToLoopBegin() {
		currentIndex = loopBeginIndex;
	}
	
	public final int getStatementCount() {
		return statements.size();
	}
	
	public final ActionStatement getStatement(final int index) {
		return statements.get(index);
	}
	
	public final boolean isEmpty() {
		return getStatementCount() == 0;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		
		final int count = statements.size();
		for(int i = 0; i < count; i ++) {
			final ActionStatement as = statements.get(i);
			sb.append(as.toString());
			sb.append(DELIMITER);
		}
		
		return sb.toString();
	}
	
	final void nextStatement() {
		currentIndex ++;
		
		currentStatement = statements.get(currentIndex);
		
		if(currentIndex >= statements.size()) {
			setEnded(true);
		}
		
	}
	
	public final ActionStatement getCurrentStatement() {
		return currentStatement;
	}
	
	final int getPauseDelay() {
		return pauseDelay;
	}

	final void setPauseDelay(final int pauseDelay) {
		this.pauseDelay = pauseDelay;
	}

	final boolean isEnded() {
		return ended;
	}

	final void setEnded(final boolean ended) {
		this.ended = ended;
	}

	final boolean isMoving() {
		return moving;
	}

	final void setMoving(final boolean moving) {
		this.moving = moving;
	}

	final float getSpeedY() {
		return speedY;
	}

	final void setSpeedY(final float speedY) {
		this.speedY = speedY;
	}

	final float getSpeedX() {
		return speedX;
	}

	final void setSpeedX(final float speedX) {
		this.speedX = speedX;
	}

	final boolean isWalking() {
		return walking;
	}

	final void setWalking(final boolean walking) {
		this.walking = walking;
	}
}
