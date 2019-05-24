package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.arming.Weapon;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.common.BotController;
import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.debug.CollisionShowRect;
import ru.ancevt.maed.gameobject.area.AreaHook;
import ru.ancevt.maed.gameobject.area.AreaWater;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

abstract public class Actor extends Animated implements IDirectioned, IMoveable, IAnimated,
IDestroyable, ITight, IResettable, IGravitied, IControllable {

	private static int JUMP_TIME = 20;
	private static int ATTACK_TIME = 20;
	private static int UNUTTAINABLE_TIME = 20;
	private static int WATER_TIME = 20;
	private static int HOOK_TIME = 20;
	
	private boolean collisionEnabled;
	private float collisionX, collisionY, collisionWidth, collisionHeight;
	private boolean collisionVisible;
	private Controller controller;
	private float weight;
	private float speed;
	private float startX, startY;
	private int startDirection;
	private ICollisioned floor;
	private float velocityX, velocityY;
	private boolean gravityEnabled;
	private int maxHealth, health;
	private float movingSpeedX, movingSpeedY;
	private float weaponX, weaponY;
	private float jumpPower;
	private int unattainableTime;
	private int attackTime;
	
	private int jumpTime;
	private int waterTime;
	private int hookTime;
	
	private boolean jumping;
	private boolean hooked;
	protected boolean dead;
	
	private Weapon weapon;
	private boolean going;
	
	private CollisionShowRect collisionShowRect;

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
		if(dead) return;
		
		boolean act = false;
		
		if(controller.isRight()) {
			go(1);
			act = true;
		} 
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
		
		if(going) {
			if(!(this instanceof UserActor))
				setVelocityX(getVelocityX() + speed * getDirection());
		}
	}
	
	public final void stop() {
		going = false;
	}

	public final void go(int direction) {
		setDirection(direction);
		if(hooked) return;
		setAnimation(AKey.WALK, true);
		going = true;
		if(this instanceof UserActor) {
			setVelocityX(getVelocityX() + speed * getDirection());
		}
	}
	
	@Override
	public boolean isInWater() {
		return waterTime > 0;
	}
	
	public void attack() {
		attackTime = ATTACK_TIME;
		
		if(hooked) setAnimation(AKey.HOOK_ATTACK);
		
		World.getWorld().actorAttack(this);
	}
	
	public void jump() {
		if(!jumping && getFloor() != null) {
			if(this instanceof UserActor) jumping = true;
 			setVelocityY(-jumpPower);
			jumpTime = JUMP_TIME;
			setAnimation(AKey.JUMP);
		} else {
			if(Math.abs(getMovingSpeedX()) > 0) {
				setAnimation(AKey.WALK);
			} else {
				setAnimation(AKey.IDLE);
			}
		}
	}
	
	
	public float getWeaponX() {
		return weaponX;
	}
	
	public float getWeaponY() {
		return weaponY;
	}
	
	public void setWeaponXY(float weaponX, float weaponY) {
		this.weaponX = weaponX;
		this.weaponY = weaponY;
	}
	
	public void setJumpPower(float value) {
		this.jumpPower = value;
	}
	
	public float getJumpPower() {
		return jumpPower;
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
	public void setCollisionArea(float collisionX, float collisionY, float collisionWidth, float collisionHeight) {
		this.collisionX = collisionX;
		this.collisionY = collisionY;
		this.collisionWidth = collisionWidth;
		this.collisionHeight = collisionHeight;
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
		return collisionWidth;
	}

	@Override
	public float getCollisionHeight() {
		return collisionHeight;
	}

	@Override
	public void setCollisionVisible(boolean value) {
		this.collisionVisible = value;

		if(collisionShowRect != null) collisionShowRect.removeFromParent();
		
		if(collisionVisible) {
			collisionShowRect = new CollisionShowRect(this);
			add(collisionShowRect);
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
		velocityX += value;
	}
	
	@Override
	public void toVelocityY(float value) {
		velocityY += value;
	}

	@Override
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	@Override
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	@Override
	public void setVelocity(float velocityX, float velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}

	@Override
	public float getVelocityX() {
		return velocityX;
	}

	@Override
	public float getVelocityY() {
		return velocityY;
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
		setCollisionEnabled(true);
		setHealth(getMaxHealth());
		hooked = false;
		dead = false;
		setRotation(0);
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
	public void setHealth(int value) {
		if(value < 0) value = 0; else
		if(value > maxHealth) value = maxHealth;
		this.health = value;
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
		unattainableTime = UNUTTAINABLE_TIME;
		addHealth(-damagingFrom.getDamagingPower());
		setVelocity(-getDirection() * 5, -2);
	}
	
	@Override
	public void setUnattainable(boolean value) {
	}
	
	@Override
	public boolean isUnattainable() {
		return unattainableTime > 0;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}
