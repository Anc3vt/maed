package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.common.BotController;
import ru.ancevt.maed.common.Direction;
import ru.ancevt.maed.gameobject.actionprogram.ActionProcessor;
import ru.ancevt.maed.gameobject.actionprogram.ActionProgram;
import ru.ancevt.maed.gameobject.area.AreaTrigger;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class Bot extends Actor_legacy implements IActioned, IDamaging {

	private int damagingPower;
	private boolean reactsOnMarkers;
	private boolean alwaysFaceToface;
	private ActionProcessor actionProcessor;
	
	public Bot(MapkitItem mapKitItem, int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setController(new BotController());
		actionProcessor = new ActionProcessor(this);
		actionProcessor.setController(getController());
		//setWeapon(new DefaultWeapon());\
		getController().setEnabled(true);
	}

	@Override
	public Bot copy() {
		final Bot result = new Bot(getMapkitItem(), Map.getCurrentMap().getNextFreeGameObjectId());
		
		result.setXY(getX(), getY());
		result.setStartXY(getStartX(), getStartY());
		result.setWeight(getWeight());
		result.setCollisionArea(
			getCollisionX(),
			getCollisionY(),
			getCollisionWidth(),
			getCollisionHeight()
		);
		result.setDirection(getDirection());
		result.setMaxHealth(getMaxHealth());
		result.setJumpPower(getJumpPower());
		result.setSpeed(getSpeed());
		result.setWeaponLocation(getWeaponX(), getWeaponY());
		result.setDamagingPower(getDamagingPower());
		result.setGravityEnabled(isGravityEnabled());
		result.setPushable(isPushable());
		result.setCollisionEnabled(isCollisionEnabled());
		result.setReactsOnMarkers(isReactsOnMarkers());
		result.setAlwaysFaceToface(isAlwaysFaceToface());
		result.setFloorOnly(isFloorOnly());
		
		return result;
	}

	@Override
	public void onCollide(ICollisioned collideWith) {

		if(reactsOnMarkers && collideWith instanceof AreaTrigger) {
			final AreaTrigger areaTrigger = (AreaTrigger) collideWith;
			if(areaTrigger.isJump() 
					&& (
						(getDirection() == Direction.LEFT && areaTrigger.isLeft()) ||
						(getDirection() == Direction.RIGHT && areaTrigger.isRight())
					)) {
				jump();
			}
			if(areaTrigger.isLeft()) {
				getController().setLeft(true);
			}
			if(areaTrigger.isRight()) {
				getController().setRight(true);
			}
			if (areaTrigger.isStop()) {
				getController().setLeft(false);
				getController().setRight(false);
			}
		}
		
		
	}
	
	@Override
	public void onEachFrame() {
		super.onEachFrame();
		
		if(isAlwaysFaceToface()) {
			final float userX = World.getWorld().getUserActor().getX();
			setDirection(userX < getX() ? Direction.LEFT : Direction.RIGHT);
		}
	}

	public boolean isReactsOnMarkers() {
		return reactsOnMarkers;
	}

	public void setReactsOnMarkers(boolean reactsOnMarkers) {
		this.reactsOnMarkers = reactsOnMarkers;
	}

	public boolean isAlwaysFaceToface() {
		return alwaysFaceToface;
	}

	public void setAlwaysFaceToface(boolean alwaysFaceToface) {
		this.alwaysFaceToface = alwaysFaceToface;
	}

	@Override
	public void setDamagingPower(int damagingPower) {
		this.damagingPower = damagingPower;
	}

	@Override
	public int getDamagingPower() {
		return this.damagingPower;
	}

	@Override
	public void setDamagingOwnerActor(Actor_legacy character) {
		
	}

	@Override
	public Actor_legacy getDamagingOwnerActor() {
		return null;
	}
	
	@Override
	public String toString() {
		return "[Bot, id: " + getId() + "]";
	}

	@Override
	public void setActionProgram(ActionProgram actionProgram) {
		System.out.println(actionProgram);
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

	
}
