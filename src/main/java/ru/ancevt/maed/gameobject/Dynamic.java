package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.map.MapkitItem;

public class Dynamic extends Sprite implements 
		ITight, IGravitied, 
		IScalable, IRotatable, IAlphable, IMoveable, IResettable {

	private boolean collisionEnabled, collisionVisible;
	private boolean gravityEnabled, floorOnly, pushable;
	private MapkitItem mapKitItem;
	private float collisionX, collisionY, collisionW, collisionH;
	private float startX, startY, movingSpeedX, movingSpeedY;
	private float speed, weight;
	private float velX, velY;
	private ICollisioned floor;
	private int id;
	
	public Dynamic(final MapkitItem mapKitItem, final int gameObjectId) {
		super(mapKitItem.getTexture());
		this.mapKitItem = mapKitItem;
		this.id = gameObjectId;
	}
	
	@Override
	public void setCollisionEnabled(boolean b) {
		collisionEnabled = b;
	}

	@Override
	public boolean isCollisionEnabled() {
		return collisionEnabled;
	}

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		collisionX = x;
		collisionY = y;
		collisionW = w;
		collisionH = h;
	}

	@Override
	public float getCollisionX() {
		return collisionX;
	}

	@Override
	public float getCollisionY() {
		return collisionY;
	}

	@Override
	public float getCollisionWidth() {
		return collisionW;
	}

	@Override
	public float getCollisionHeight() {
		return collisionH;
	}

	@Override
	public void setCollisionVisible(boolean b) {
		this.collisionVisible = b;
	}

	@Override
	public boolean isCollisionVisible() {
		return collisionVisible;
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		
	}

	@Override
	public boolean isSaveable() {
		return true;
	}

	@Override
	public MapkitItem getMapkitItem() {
		return mapKitItem;
	}

	@Override
	public IGameObject copy() {
		
		return null;
	}

	@Override
	public void setStartX(float x) {
		this.startX = x;
	}

	@Override
	public void setStartY(float y) {
		this.startY = y;
	}

	@Override
	public void setStartXY(float x, float y) {
		this.startX = x;
		this.startY = y;
	}

	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public float getStartX() {
		return startX;
	}

	@Override
	public float getStartY() {
		return startY;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public float getWeight() {
		return weight;
	}

	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public void setFloor(ICollisioned floor) {
		this.floor = floor;
	}

	@Override
	public ICollisioned getFloor() {
		return floor;
	}

	@Override
	public void setVelocityX(float velocityX) {
		this.velX = velocityX;
	}

	@Override
	public void setVelocityY(float velocityY) {
		this.velY = velocityY;
	}

	@Override
	public void setVelocity(float vX, float vY) {
		this.velX = vX;
		this.velY = vY;
	}

	@Override
	public float getVelocityX() {
		return this.velX;
	}

	@Override
	public float getVelocityY() {
		return this.velY;
	}

	@Override
	public void setGravityEnabled(boolean b) {
		gravityEnabled = b;
	}

	@Override
	public boolean isGravityEnabled() {
		return gravityEnabled;
	}

	@Override
	public void setFloorOnly(boolean b) {
		this.floorOnly = b;
	}

	@Override
	public boolean isFloorOnly() {
		return this.floorOnly;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void reset() {
		setXY(startX, startY);
	}

	@Override
	public void setPushable(boolean b) {
		this.pushable = b;
	}

	@Override
	public boolean isPushable() {
		return this.pushable;
	}

	@Override
	public void move(float toX, float toY) {
		moveX(toX);
		moveY(toY);
	
	}
	
	@Override
	public void moveX(float value) {
		movingSpeedX = value;
		super.moveX(value);
	}
	
	@Override
	public void moveY(float value) {
		movingSpeedY = value;
		super.moveY(value);
	}
	
	@Override
	public float getMovingSpeedX() {
		return movingSpeedX;
	}

	@Override
	public float getMovingSpeedY() {
		return movingSpeedY;
	}

	

	@Override
	public void toVelocityX(float value) {
		setVelocityX(getVelocityX() + value);
	}

	@Override
	public void toVelocityY(float value) {
		setVelocityY(getVelocityY() + value);
		
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInWater() {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public void process() {
//		if(movingSpeedX != 0.0f || movingSpeedY != 0.0f) {
//			if(Math.abs(movingSpeedX) < 0.1f) movingSpeedX = 0;
//			if(Math.abs(movingSpeedY) < 0.1f) movingSpeedY = 0;
//			
//			movingSpeedX *= 0.95f;
//			movingSpeedY *= 0.95f;
//		}
//	}


}
