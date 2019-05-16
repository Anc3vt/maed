package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.gameobject.actorprogram.ActionProcessor;
import ru.ancevt.maed.gameobject.actorprogram.ActionProgram;
import ru.ancevt.maed.gameobject.area.AreaTrigger;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class Bot extends Actor implements IActioned, IDamaging {

	private int damagingPower;
	private ActionProcessor actionProcessor;
	private boolean reactsOnTriggers;
	private boolean face2face;
	
	public Bot(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
		actionProcessor = new ActionProcessor(this);
		actionProcessor.setController(getController());
		
	}
	
	@Override
	public IGameObject copy() {
		final Bot bot = new Bot(getMapkitItem(), World.getWorld().getMap().getNextFreeGameObjectId());
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
	public void setHealth(int health) {
		if(health <= 0) {
			death();
		}
		super.setHealth(health);
	}
	
	private final void death() {
		setAnimation(AKey.DAMAGE);
		setRotation(-180);
		dead = true;
		setDamagingPower(0);
		setCollisionEnabled(false);
		setVelocityY(-5);
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
		if(collideWith instanceof IDestroyable) {
			final IDestroyable d = (IDestroyable)collideWith;
			d.onDamage(this);
		}
		
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
				
			if(!at.isJump() && at.isRight() && getDirection() == -1) {
				if(getController().isLeft()) getController().setLeft(false);
				go(1);
			}
			if(!at.isJump() && at.isLeft() && getDirection() == 1) {
				if(getController().isRight()) getController().setRight(false);
				go(-1);
			}
		}
		
		
		
		if (collideWith instanceof ITight && getX() < collideWith.getX() && getFloor() != collideWith) {
			setDirection(-1);
		} else
		if (collideWith instanceof ITight && getX() > collideWith.getX() + collideWith.getCollisionX() + collideWith.getCollisionWidth()) {
			setDirection(1);
		}
		
		super.onCollide(collideWith);
	}
	
	@Override
	public void process() {
		super.process();
		if(face2face) {
			final float userX = World.getWorld().getUserActor().getX();
			if(userX < getX()) go(-1); else go(1);
		} 
		
		
	}

}
