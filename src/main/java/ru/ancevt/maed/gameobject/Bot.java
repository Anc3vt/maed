package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.gameobject.actionprogram.ActionProcessor;
import ru.ancevt.maed.gameobject.actionprogram.ActionProgram;
import ru.ancevt.maed.gameobject.area.AreaTrigger;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class Bot extends Actor implements IActioned, IDamaging {

	private static final int REVERSE_DELAY = 50;
	
	private int damagingPower;
	private ActionProcessor actionProcessor;
	private boolean reactsOnTriggers;
	private boolean face2face;
	
	private int reverseDelay;
	
	public Bot(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
		actionProcessor = new ActionProcessor(this);
	}
	
	@Override
	public IGameObject copy() {
		final Bot bot = new Bot(getMapkitItem(), Map.getCurrentMap().getNextFreeGameObjectId());
		bot.setDamagingPower(damagingPower);
		bot.setActionProgram(actionProcessor.getActionProgram().copy());
		bot.setReactsOnTriggers(reactsOnTriggers);
		bot.setFace2Face(face2face);
		bot.setX(getX());
		bot.setY(getY());
		bot.setSpeed(getSpeed());
		bot.setMaxHealth(getMaxHealth());
		bot.setDirection(getDirection());
		bot.setStartDirection(getStartDirection());
		bot.setWeight(getWeight());
		bot.setJumpPower(getJumpPower());
		bot.setCollisionArea(getCollisionX(), getCollisionY(), getCollisionWidth(), getCollisionHeight());
		
		return bot;
	}

	@Override
	public void setDamagingPower(int damagingPower) {
		this.damagingPower = damagingPower;
	}

	@Override
	public int getDamagingPower() {
		return damagingPower;
	}

	@Override
	public void setDamagingOwnerActor(Actor character) {
		
	}

	@Override
	public Actor getDamagingOwnerActor() {
		return null;
	}

	@Override
	public void setActionProgram(ActionProgram actionProgram) {
		actionProcessor.setActionProgram(actionProgram);
	}

	@Override
	public ActionProgram getActionProgram() {
		return actionProcessor.getActionProgram();
	}

	@Override
	public void actionProcess() {
		actionProcessor.process();
	}

	public void setReactsOnTriggers(boolean b) {
		this.reactsOnTriggers = b;
	}

	public void setFace2Face(boolean b) {
		this.face2face = b;
	}

	public boolean isFace2Face() {
		return face2face;
	}

	public boolean isReactsOnTriggers() {
		return reactsOnTriggers;
	}
	
	@Override
	public void onCollide(ICollisioned collideWith) {
		if(collideWith instanceof AreaTrigger ) {
			final AreaTrigger at = (AreaTrigger)collideWith;
			
			if(at.isJump() && !at.isLeft() && !at.isRight()) {
				jump();
			} 
			if(at.isJump() && at.isLeft() && getDirection() < 0) {
				jump();
			} 
			if(at.isJump() && at.isRight() && getDirection() > 0) {
				jump();
			} 
				
			if(!at.isJump() && at.isRight() && getDirection() == -1) go(1);
			if(!at.isJump() && at.isLeft() && getDirection() == 1) go(-1);
		}
		
		if (collideWith instanceof ITight && collideWith.getY() < getY()) {
			reverseDelay ++;
			
			if(reverseDelay > REVERSE_DELAY) {
				reverseDelay = 0;
				setDirection(-getDirection());
			}
		}
		
		super.onCollide(collideWith);
	}
	
	@Override
	public void process() {
		if(face2face) {
			final float userX = World.getWorld().getUserActor().getX();
			if(userX < getX()) go(-1); else go(1);
		}
		super.onEachFrame();
	}

}
