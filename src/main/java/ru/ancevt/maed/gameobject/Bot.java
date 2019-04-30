package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.gameobject.actionprogram.ActionProcessor;
import ru.ancevt.maed.gameobject.actionprogram.ActionProgram;
import ru.ancevt.maed.gameobject.area.AreaTrigger;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class Bot extends Actor implements IActioned, IDamaging {

	private int damagingPower;
	private ActionProcessor actionProcessor;
	private boolean reactsOnTriggers;
	private boolean face2face;
	private AreaTrigger lastAreaTrigger;
	
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
		bot.setStartX(getStartX());
		bot.setStartY(getStartY());;
		bot.setSpeed(getSpeed());
		bot.setMaxHealth(getMaxHealth());
		bot.setDirection(getDirection());
		bot.setWeight(getWeight());
		bot.setJumpPower(getJumpPower());
		
		
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
		if(collideWith instanceof AreaTrigger && lastAreaTrigger != collideWith) {
			final AreaTrigger at = (AreaTrigger)collideWith;
			
			lastAreaTrigger = at;
			
			if(at.isJump() && at.isRight() && getDirection() == 1) jump(); else
			if(at.isJump() && at.isLeft() && getDirection() == -1) jump(); else
			
			if(at.isLeft() && getDirection() == 1) setDirection(-1); else
			if(at.isRight() && getDirection() == -1) setDirection(2);
		}
		
		
		super.onCollide(collideWith);
	}
	
	@Override
	public void onEachFrame() {
		if(face2face) {
			final float userX = World.getWorld().getUserActor().getX();
			if(userX < getX()) go(-1); else go(1);
		}
		super.onEachFrame();
	}

}
