package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.common.BotController;
import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.gameobject.area.AreaHook;
import ru.ancevt.maed.gameobject.area.AreaWater;
import ru.ancevt.maed.map.MapkitItem;

abstract public class Actor extends Animated implements IGameObject, IDirectioned, IMoveable, IAnimated,
IDestroyable, ITight, IResettable, IGravitied, IControllable {

	private static int JUMP_TIME = 20;
	private static int ATTACK_TIME = 20;
	private static int UNUTAINABLE_TIME = 20;
	private static int WATER_TIME = 20;
	private static int HOOK_TIME = 20;
	
	private boolean collisionEnabled;
	private float collX, collY, collWidth, collHeight;
	private boolean collisionVisible;
	private Controller controller;
	private float weight;
	private float speed;
	private float startX, startY;
	private int startDirection;
	private ICollisioned floor;
	private float velX, velY;
	private boolean gravityEnabled;
	private int maxHealth, health;
	private float movingSpeedX, movingSpeedY;
	private float weapX, weapY;
	private float jumpPower;
	private int unattainableTime;
	private int attackTime;
	
	private int jumpTime;
	private int waterTime;
	private int hookTime;
	
	private PlainRect collRect;
	
	private boolean jumping;
	private boolean hooked;
	protected boolean isDeath;

	public Actor(MapkitItem mapkitItem, int gameObjectId) {
		super(mapkitItem, gameObjectId);
		setPushable(true);
		setGravityEnabled(true);
		setCollisionEnabled(true);
		setAnimation(AKey.IDLE);
		setController(new BotController());
		
	}
	@Override
	public void process() {
		if(isDeath) return;
		
		boolean act = false;
		if(controller.isRight()) {
			go(1);
			act = true;
		} else 
		if(controller.isLeft()){
			go(-1);
			act = true;
		} 
		if(controller.isA() ) {
			jump();
			act = true;
		}
		
		if(jumpTime > 0 && controller.isA()){
			setVelocityY(-jumpPower);
		}
		
		if(waterTime > 0  && controller.isA()) {
			setVelocityY(-0.8f);
		}
		
		if(!act) setAnimation(AKey.IDLE, true);
		
		if(getVelocityY() < 0) {
			setAnimation(AKey.JUMP);
		} else {
			if(getFloor() == null) 
				setAnimation(AKey.FALL);
		}
		
		if(jumpTime > 0) jumpTime --;
	
		if(!controller.isA()) jumping = false;
		
		if(getDirection() != 0 && getFloor() != null && !(this instanceof UserActor)) {
			setAnimation(AKey.WALK);
			//dtoVelocityX(speed * getDirection());
		}
		
		if(getFloor() != null && getFloor() instanceof IMoveable) {
			moveX(((IMoveable)getFloor()).getMovingSpeedX());
		}

		if(unattainableTime > 0) {
			setVisible(!isVisible());
			unattainableTime--;
		} else {
			if(unattainableTime == 0) setVisible(true);
		}
		
		if (controller.isB() && attackTime == 0) attack();
		
		if(attackTime > 0) {
			attackTime --;
			
			if(movingSpeedX == 0)
				setAnimation(AKey.ATTACK);
			else 
				setAnimation(AKey.WALK_ATTACK);
			
			if(getFloor() == null) {
				if (movingSpeedY > 0) setAnimation(AKey.FALL_ATTACK);
				if (movingSpeedY < 0) setAnimation(AKey.JUMP_ATTACK); 
			}
		}
		
		if(waterTime > 0) {
			waterTime--;
		}
		
		if(hookTime > 0) hookTime--;
		
		if(hooked) {
			if(controller.isB())
				setAnimation(AKey.HOOK_ATTACK);
			else
				setAnimation(AKey.HOOK);
		}
		
		if (hooked && controller.isA() && !jumping) {
			hooked = false;
			jumping = true;
			jump();
			setVelocityY(-5);
			setGravityEnabled(true);
			hookTime = HOOK_TIME;
		}
		
		if(isUnattainable()) {
			setAnimation(AKey.DAMAGE);
		}
	}
	
	@Override
	public boolean isInWater() {
		return waterTime > 0;
	}
	
	public void attack() {
		attackTime = ATTACK_TIME;
		
		if(hooked) setAnimation(AKey.HOOK_ATTACK);
	}
	
	
	public void jump() {
		if(!jumping && getFloor() != null) {
			if(this instanceof UserActor) jumping = true;
 			setVelocityY(-jumpPower);
			jumpTime = JUMP_TIME;
			setAnimation(AKey.JUMP);
		} else {
			setAnimation(AKey.IDLE);
		}
	}
	
	public final void go(int direction) {
		setDirection(direction);
		if(hooked) return;
		setAnimation(AKey.WALK, true);
		setVelocityX(getVelocityX() + speed * direction);
	}
	
	public float getWeaponX() {
		return weapX;
	}
	
	public float getWeaponY() {
		return weapY;
	}
	
	public void setWeaponXY(float x, float y) {
		
	}
	
	public void setJumpPower(float value) {
		this.jumpPower = value;
	}
	
	public float getJumpPower() {
		return jumpPower;
	}
	
	public void onHealthChanged(int health) {
		
	}
	
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

		if(collRect != null && collRect.hasParent()) {
			collRect.removeFromParent();
			collRect = null;
		}
		
		if(collRect == null) {
			collRect = new PlainRect(Color.DARK_GREEN);
			collRect.setX(getCollisionX());
			collRect.setY(getCollisionY());
			collRect.setWidth(getCollisionWidth());
			collRect.setHeight(getCollisionHeight());
			add(collRect);
		}
		
		if (!collisionVisible && collRect != null) {
			collRect.removeFromParent();
			collRect = null;
		}
	}

	@Override
	public boolean isCollisionVisible() {
		return collisionVisible;
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		if(collideWith instanceof AreaWater) {
			waterTime = WATER_TIME;
		}
		if(this instanceof UserActor && collideWith instanceof AreaHook && !hooked && hookTime == 0) {
			setGravityEnabled(false);
			setX(collideWith.getX());
			setY(collideWith.getY());
			hooked = true;
		}
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
	public void toVelocityX(float value) {
		velX += value;
	}
	
	@Override
	public void toVelocityY(float value) {
		velY += value;
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
		setDirection(getStartDirection());
		setGravityEnabled(true);
		hooked = false;
		isDeath = false;
		setRotation(0);
		setCollisionArea(-8, -16, 16, 32);
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
		
		onHealthChanged(health);
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

	@Override
	public void setStartDirection(int value) {
		this.startDirection = value;
	}

	@Override
	public int getStartDirection() {
		return startDirection;
	}

	@Override
	public void onDamage(IDamaging damagingFrom) {
		if(isUnattainable()) return;
		
		setUnattainable(true);
		setAnimation(AKey.DAMAGE);
		unattainableTime = UNUTAINABLE_TIME;
		addHealth(-damagingFrom.getDamagingPower());
	}
	
	@Override
	public void setUnattainable(boolean value) {
	}
	
	@Override
	public boolean isUnattainable() {
		return unattainableTime > 0;
	}
}
