package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.text.BitmapText;
import ru.ancevt.maed.common.AnimationKey;
import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.common.Direction;
import ru.ancevt.maed.common.SoundKey;
import ru.ancevt.maed.gameobject.weapon.Weapon;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

abstract public class Actor_legacy extends Animated implements 
			IGameObject, IDirectioned, IMoveable, IAnimated,
			IDestroyable, ITight, IResettable, IGravitied, IControllable  {

	private static final int ATTACK_TIME = 20;
	private static final int JUMP_TIME = 4;
	private static final int DAMAGING_TIME = 30;
	
	private int attackTime;
	private int jumpTime;
	private int damagingTime;
	
	private float startX, startY, movingSpeedX, movingSpeedY;
	private float speed;
	private int health, maxHealth;
	private boolean collisionEnabled;
	private float collisionX, collisionY, collisionWidth, collisionHeight;
	private float weight;
	private PlainRect collisionRect;
	private ICollisioned floor;
	private float velocityX, velocityY;
	private float jumpPower;
	private Controller controller;
	
	protected BitmapText bitmapTextDebug;
	private boolean fallEnabled;
	private float weaponLocationX, weaponLocationY;
	private boolean alive;
	private Weapon weapon;
	private boolean pushable;
	
	public Actor_legacy(final MapkitItem mapkitItem, final int gameObjectId) {
		super(mapkitItem, gameObjectId);
		setPushable(true);
		setGravityEnabled(true);
		setAlive(true);
		setCollisionEnabled(true);
		setAnimation(AnimationKey.IDLE);
	}

	public final void debug(final Object o) {
		if(o == null && bitmapTextDebug != null && bitmapTextDebug.hasParent()) {
			bitmapTextDebug.getParent().remove(bitmapTextDebug);
			bitmapTextDebug = null;
		}
		
		if(bitmapTextDebug == null) 
			bitmapTextDebug = new BitmapText() {
			@Override
			public void onEachFrame() {
				getParent().add(this);
				super.onEachFrame();
			}
		};
	
		bitmapTextDebug.setText(o != null ? o.toString() : null);
		add(bitmapTextDebug);
		bitmapTextDebug.setScale(0.30f, 0.30f);
	}
	
	public final void debug(
		final Object o, 
		final float offsetX, 
		final float offsetY) {
		
		debug(o);
		if(bitmapTextDebug != null && bitmapTextDebug.hasParent())
			bitmapTextDebug.setXY(offsetX, offsetY);
	}
	
	@Override
	public boolean isSaveable() {
		return true;
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
		setStartX(x);
		setStartY(y);
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
	public void setMaxHealth(int health) {
		this.maxHealth = health;
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
		
		if(health <= 0 && isAlive()) death();
		
		onHealthChanged(health);
	}

	public void onHealthChanged(float health) {
		
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void addHealth(int toHealth) {
		if(toHealth < 0) {
			
			if(getHealth() > -toHealth)
				getMapkitItem().playSound(SoundKey.DAMAGE, 0);
			
			if(damagingTime > 0) return;
			
			setAnimation(AnimationKey.DAMAGE);
			
			damagingTime = DAMAGING_TIME;
			setVelocity(
				(Direction.check(this, Direction.LEFT) ? 1 : -1) * 5,
			-1);
			
		}
		
		setHealth(getHealth() + toHealth);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(final boolean alive) {
		this.alive = alive;
		setScaleY(alive ? 1.0f : -1.0f);
		setCollisionEnabled(alive);
		
		if(!alive)
			setVelocityY(-4);
	}
	
	public void death() {
		getMapkitItem().playSound(SoundKey.DEATH);
		setAlive(false);
		setHealth(0);
	}
	
	@Override
	public void setCollisionEnabled(boolean collisionEnabled) {
		this.collisionEnabled = collisionEnabled;
	}
	
	@Override
	public boolean isCollisionEnabled() {
		return collisionEnabled;
	}
	

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		this.collisionX = x;
		this.collisionY = y;
		this.collisionWidth = w;
		this.collisionHeight = h;
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
	public void reset() {
		setXY(getStartX(), getStartY());
		setHealth(getMaxHealth());
		getController().reset();		
		setAlive(true);
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
	public void setCollisionVisible(boolean b) {
		if(b) {
			if(isCollisionVisible()) return;
			
			collisionRect = new PlainRect(
				getCollisionWidth(), 
				getCollisionHeight()
			);
			
			collisionRect.setColor(Color.GREEN);
			collisionRect.setAlpha(0.25f);
			collisionRect.setXY(getCollisionX(), getCollisionY());
			add(collisionRect);
		} else {
			if(collisionRect != null && collisionRect.hasParent()) {
				collisionRect.getParent().remove(collisionRect);
			}
			collisionRect = null;
		}
	}
	
	@Override
	public boolean isCollisionVisible() {
		return collisionRect != null;
	}
	
	@Override
	public void setFloorOnly(boolean b) {
	}
	
	@Override
	public boolean isFloorOnly() {
		return false;
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
		this.velocityX = velocityX;
	}

	@Override
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	@Override
	public void setVelocity(float vX, float vY) {
		setVelocityX(vX);
		setVelocityY(vY);
	}

	@Override
	public float getVelocityX() {
		return velocityX;
	}

	@Override
	public float getVelocityY() {
		return velocityY;
	}
	
	public void attack() {
		attackTime = ATTACK_TIME;
		if(getWeapon() != null)
			World.getWorld().actorAttack(this, getWeapon());
	}
	
	public void jump() {
		getMapkitItem().playSound(SoundKey.JUMP, 0);
		setVelocityY(-getJumpPower());
	}
	
	public void walk() {
		go(getDirection());
	}
	
	public void go(final int direction) {
		setDirection(direction);
		
		if(!isGravityEnabled()) return;
		
		if(Direction.check(this, Direction.RIGHT)) {
			setVelocityX(
				getVelocityX() + getSpeed()	
			);
		} else 
		if(Direction.check(this, Direction.LEFT)) {
			setVelocityX(
				getVelocityX() - getSpeed()	
			);
		}
	}

	public float getJumpPower() {
		return jumpPower;
	}

	public void setJumpPower(float jumpPower) {
		this.jumpPower = jumpPower;
	}

	
	
	@Override
	public void onEachFrame() {
		setAnimation(AnimationKey.IDLE);
		
		if(attackTime > 0) {
			if(getVelocityY() == 0)
				setAnimation(AnimationKey.ATTACK);
		}
		
		final Controller c = getController();
		
		final boolean left = c.isLeft();
		final boolean right = c.isRight();
		
		if(isAlive()) {
			if(left || right) {
				go(left ? Direction.LEFT : Direction.RIGHT);
				
				if(attackTime == 0 && !c.isB()) {
					setAnimation(AnimationKey.WALK);
				} else {
					setAnimation(AnimationKey.WALK_ATTACK);
				}
			} else 
				if(attackTime == 0 )
					setAnimation(AnimationKey.IDLE);
			
			if(c.isB()) {
				if(attackTime == 0)
					attack();
			}
			
			if(c.isA() && getFloor() != null) {
				jump();
				jumpTime = JUMP_TIME;
			}
		}
		
		if(attackTime > 1) attackTime--;
		if(attackTime == 1 && !c.isB()) attackTime--;
		
		if(jumpTime > 0) {
			jumpTime--;
			if(c.isA()) setVelocityY(getVelocityY() - 1f);
		} else c.setA(false);
		
		if(getFloor() == null) {
			
			setAnimation(
				getVelocityY() < 0 ? 
					(attackTime == 0 ? AnimationKey.JUMP : AnimationKey.JUMP_ATTACK) :
					(attackTime == 0 ? AnimationKey.FALL : AnimationKey.FALL_ATTACK)
						
			);
		} else if(getFloor() instanceof IMoveable){
			final IMoveable moveableFloor = (IMoveable) getFloor();
			final float toX = moveableFloor.getMovingSpeedX();
			final float toY = moveableFloor.getMovingSpeedY();
			
			move(toX, toY);
		}
		
		if(damagingTime > 0) {
			damagingTime--;
			setAlpha((damagingTime/2) % 2 == 0 ? 1.0f : 0.0f);
		}
		
		movingSpeedX = 
		movingSpeedY = 0.0f;
		super.onEachFrame();
	}
	
	protected boolean isAttackingNow() {
		return attackTime > 0;
	}

	@Override
	public Controller getController() {
		return controller;
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void setGravityEnabled(boolean b) {
		this.fallEnabled = b;
		
		if(!fallEnabled) 
			setVelocity(0f, 0f);

	}
	
	@Override
	public boolean isGravityEnabled() {
		return fallEnabled;
	}

	public float getWeaponY() {
		return weaponLocationY;
	}

	public void setWeaponY(float weaponLocationY) {
		this.weaponLocationY = weaponLocationY;
	}

	public float getWeaponX() {
		return weaponLocationX;
	}

	public void setWeaponX(float weaponLocationX) {
		this.weaponLocationX = weaponLocationX;
	}
	
	public void setWeaponLocation(final float x, final float y) {
		setWeaponX(x);
		setWeaponY(y);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		weapon.setOwner(this);
	}

	@Override
	public void setAnimation(int animationKey, boolean loop) {
		if(damagingTime > 0 || animationKey == getAnimation()) return;
		
		super.setAnimation(animationKey, loop);
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		
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
}






























