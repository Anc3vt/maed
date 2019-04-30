package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.common.AnimationKey;
import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.map.MapkitItem;

public class Actor extends Animated implements IGameObject, IDirectioned, IMoveable, IAnimated,
IDestroyable, ITight, IResettable, IGravitied, IControllable {

	public Actor(MapkitItem mapKitItem, int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setPushable(true);
		setGravityEnabled(true);
		setCollisionEnabled(true);
		setAnimation(AnimationKey.IDLE);
	}

	private boolean collisionEnabled;
	private float collX, collY, collWidth, collHeight;
	private boolean collisionVisible;
	private Controller controller;
	private float weight;
	private float speed;
	private float startX, startY;
	private ICollisioned floor;
	private float velX, velY;
	private boolean gravityEnabled;
	private int maxHealth, health;
	private float movingSpeedX, movingSpeedY;
	
	
	@Override
	public void setCollisionEnabled(boolean value) {
		collisionEnabled = value;
	}

	@Override
	public boolean isCollisionEnabled() {
		return collisionEnabled;
	}

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		collX = x;
		collY = y;
		collWidth = w;
		collHeight = h;
	}

	@Override
	public float getCollisionX() {
		return collX;
	}

	@Override
	public float getCollisionY() {
		return collY;
	}

	@Override
	public float getCollisionWidth() {
		return collWidth;
	}

	@Override
	public float getCollisionHeight() {
		return collHeight;
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
		// TODO Auto-generated method stub
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public Controller getController() {
		return controller;
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
		velX = velocityX;
	}

	@Override
	public void setVelocityY(float velocityY) {
		velY = velocityY;
	}

	@Override
	public void setVelocity(float vX, float vY) {
		velX = vX;
		velY = vY;
	}

	@Override
	public float getVelocityX() {
		return velX;
	}

	@Override
	public float getVelocityY() {
		return velY;
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
	public void reset() {
		setX(startX);
		setY(startY);
	}

	@Override
	public void setFloorOnly(boolean b) {
		
	}

	@Override
	public boolean isFloorOnly() {
		return false;
	}

	@Override
	public void setPushable(boolean b) {
		
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public void setMaxHealth(int health) {
		maxHealth = health;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public void setHealth(int health) {
		if(health < 0) health = 0; else
		if(health > maxHealth) health = maxHealth;
		this.health = health;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void addHealth(int toHealth) {
		setHealth(getHealth() + toHealth);
	}

	@Override
	public void setStartX(float x) {
		startX = x;
	}

	@Override
	public void setStartY(float y) {
		startY = y;
	}

	@Override
	public void setStartXY(float x, float y) {
		startX = x;
		startY = y;
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
	public float getMovingSpeedX() {
		return movingSpeedX;
	}

	@Override
	public float getMovingSpeedY() {
		return movingSpeedY;
	}
	

}
