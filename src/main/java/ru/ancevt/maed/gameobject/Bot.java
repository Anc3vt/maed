package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.common.BotController;
import ru.ancevt.maed.gameobject.action.ActionProgram;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.MapkitItem;

public class Bot extends Actor implements IActioned, IDamaging {

	private int damagingPower;
	private boolean reactsOnMarkers;
	private boolean alwaysFaceToface;
	private ActionProgram actionProgram;
	
	public Bot(MapkitItem mapKitItem, int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setController(new BotController());
		
		// setWeapon(new DefaultWeapon());
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
		
		if(getActionProgram() != null)
			result.setActionProgram(new ActionProgram(getActionProgram().toString()));
		
		return result;
	}

	@Override
	public void onEachFrame() {
		
		
		super.onEachFrame();
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		// TODO Auto-generated method stub
		
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
	public void setActionProgram(ActionProgram actionProgram) {
		this.actionProgram = actionProgram;
		
		/*
		final ActionProgramDemonstrator demon = new ActionProgramDemonstrator();
		demon.setActionProgram(actionProgram);
		
		final PlainRectangle rect = new PlainRectangle(200, 800);
		rect.setColor(Color.BLACK);
		rect.setAlpha(0.75f);
		
		final DisplayObjectContainer cont = D2D2.getCurrentCanvas().getRoot(); 
		
		cont.add(rect, 0, 0);
		cont.add(demon, 8, 64);
		*/
	}

	@Override
	public ActionProgram getActionProgram() {
		return actionProgram;
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
	public void setDamagingOwnerActor(Actor character) {
		
	}

	@Override
	public Actor getDamagingOwnerActor() {
		return null;
	}
	
	@Override
	public String toString() {
		return "[Bot, id: " + getId() + "]";
	}

	
}
