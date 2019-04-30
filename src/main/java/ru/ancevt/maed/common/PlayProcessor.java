package ru.ancevt.maed.common;

import ru.ancevt.maed.gameobject.Actor_legacy;
import ru.ancevt.maed.gameobject.IActioned;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IDamaging;
import ru.ancevt.maed.gameobject.IDestroyable;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.IGravitied;
import ru.ancevt.maed.gameobject.IHookable;
import ru.ancevt.maed.gameobject.IMoveable;
import ru.ancevt.maed.gameobject.ITight;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.gameobject.area.AreaDoorTeleport;
import ru.ancevt.maed.gameobject.area.AreaHook;
import ru.ancevt.maed.world.IWorld;

public class PlayProcessor {

	private static final float DEFAULT_GRAVITY = 1f;
	private static final int DEFAULT_SPEED = 60;
	
	public static final float MAX_VELOCITY_X = 20f;
	public static final float MAX_VELOCITY_Y = 10f;
	
	private IWorld world;
	
	private float gravity;
	
	private int delay;
	
	public PlayProcessor() {
		gravity = DEFAULT_GRAVITY;
		setSpeed(DEFAULT_SPEED);
	}
	
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public float getGravity() {
		return gravity;
	}

	public IWorld getWorld() {
		return world;
	}

	public void setWorld(IWorld world) {
		this.world = world;
	}

	public final synchronized void process() {
		IGravitied gravitied = null;
		
		for (int i = 0; i < world.getGameObjectsCount(); i++) {
			final IGameObject o1 = world.getGameObject(i);

			if(o1 instanceof IGravitied) {
				gravitied = (IGravitied)o1;
				gravitied.setFloor(null);
				processGravity(gravitied);
			}
			
			if(o1 instanceof IActioned) {
				final IActioned ac = (IActioned)o1;
				if(ac.getActionProgram() != null) {
					ac.actionProcess();
				}
			}
			
			for (int j = 0; j < world.getGameObjectsCount(); j++) {
				final IGameObject o2 = world.getGameObject(j);

				if(o1 == o2) continue;
				
				if (o1 instanceof ICollisioned &&
					o2 instanceof ICollisioned) {
					
					final ICollisioned collisioned1 = (ICollisioned)o1;
					final ICollisioned collisioned2 = (ICollisioned)o2;
					
					if(hitTest(collisioned1, collisioned2)) {
						processCollisionsHits(collisioned1, collisioned2);
					}
				}
				
			}
			
		}
	}
	
	private final void processCollisionsHits(final ICollisioned o1, final ICollisioned o2) {
		if(!o1.isCollisionEnabled() || !o2.isCollisionEnabled()) return;
		
		o1.onCollide(o2);
		o2.onCollide(o1);
		
		if(o1 instanceof ITight && o2 instanceof ITight) {
			processTight((ITight) o1, (ITight) o2);
		}
		if(o1 instanceof UserActor && o2 instanceof AreaDoorTeleport) {
			processDoorTeleport((Actor_legacy)o1, (AreaDoorTeleport)o2);
		}
		if(o1 instanceof IHookable && o2 instanceof AreaHook) {
			processHook((IHookable)o1, (AreaHook)o2);
		}
		if(o1 instanceof IDestroyable && o2 instanceof IDamaging) {
			processDamage((IDestroyable)o1, (IDamaging)o2);
		}
	}
	
	private final void processDamage(final IDestroyable o, final IDamaging damaging) {
		if(damaging.getDamagingOwnerActor() == o) return;
		
		final int damagingPower = damaging.getDamagingPower();
		
		o.addHealth(-damagingPower);
	}
	
	private final void processHook(final IHookable o, final AreaHook hook) {
		if(o.getHook() == null) {
			if(o.getY() + o.getCollisionY() > hook.getY() + hook.getCollisionY()/2 &&
			   o.getVelocityY() > 0)
					o.setHook(hook);
		}
	}

	private final void processTight(final ITight o1, final ITight o2) {
		if(!(o1 instanceof IMoveable)) return;

		if(!o1.isPushable()) return;
		
		IGravitied gravitied = null;
		if(o1 instanceof IGravitied) 
			gravitied = (IGravitied)o1;
		
		final float tx1 = o1.getCollisionX();
		final float ty1 = o1.getCollisionY();
		
		final float x1 = o1.getX() + o1.getCollisionX();
		final float y1 = o1.getY() + o1.getCollisionY();
		final float w1 = o1.getCollisionWidth();
		final float h1 = o1.getCollisionHeight();
		final float cx1 = x1 + (w1 / 2);
		final float cy1 = y1 + (h1 / 2);
		
		final float x2 = o2.getX() + o2.getCollisionX();
		final float y2 = o2.getY() + o2.getCollisionY();
		final float w2 = o2.getCollisionWidth();
		final float h2 = o2.getCollisionHeight();

		final float cy2 = y2 + (h2 / 2);
		
		final boolean checkWalls = !o2.isFloorOnly();
		
		if(checkWalls && cx1 < x2 && y1 + h1 > y2 + 8) 		o1.setX(x2 - w1 - tx1); else
		if(checkWalls && cx1 > x2 + w2 && y1 + h1 > y2 + 8) o1.setX(x2 + w2 - tx1);
		
		if(cy1 < cy2 && y1 + h1 < y2 + 11) {
			if(gravitied != null) {
				
				if(gravitied.getVelocityY() >= 0) {
					o1.setY(y2 - h1 - ty1);
					setFloorTo(gravitied, o2);
				}
				
			}  else
				o1.setY(y2 - h1 - ty1);
		} else
		if(checkWalls && cy1 > cy2 && y1 + 8 > y2 + h2 && cx1 > x2 && cx1 < x2 + w2) {
			o1.setY(y2 + h2 - ty1);
			
			if(gravitied != null) {
				gravitied.setVelocityY(0);
			}
		}
				
	}
	
	private static final void setFloorTo(final IGravitied target, final ICollisioned floor) {
		target.setVelocityY(0);
		target.setFloor(floor);
	}
	
	private static final boolean hitTest(final ICollisioned o1, final ICollisioned o2) {

		final float x1 = o1.getX() + o1.getCollisionX();
		final float y1 = o1.getY() + o1.getCollisionY();
		final float w1 = o1.getCollisionWidth();
		final float h1 = o1.getCollisionHeight();

		final float x2 = o2.getX() + o2.getCollisionX();
		final float y2 = o2.getY() + o2.getCollisionY();
		final float w2 = o2.getCollisionWidth();
		final float h2 = o2.getCollisionHeight();
		
		return
			x1 + w1 > x2 		&& 
			x1 		< x2 + w2 	&&
			y1 + h1 > y2 		&&
			y1 		< y2 + h2;
			
	}
	
	private final void processGravity(final IGravitied o) {
		if(o.isGravityEnabled()) {
			final float fallSpeed = o.getWeight() * gravity;
			o.setVelocityY(o.getVelocityY() + fallSpeed);
			
			if(o.getVelocityY() > MAX_VELOCITY_Y) o.setVelocityY(MAX_VELOCITY_Y);
			
			final float velX = o.getVelocityX();
			
			if(Math.abs(velX) > MAX_VELOCITY_X) o.setVelocityX(velX * .05f);
			
			o.setVelocityX(Math.abs(velX) < 0.1f ? 0 : velX * 0.75f);
			o.move(o.getVelocityX(), o.getVelocityY());
		}
	}
	
	private final void processDoorTeleport(final Actor_legacy actor, final AreaDoorTeleport area) {
		if(getWorld().isSwitchingRoomsNow()) return;
		
		final int targetRoomId = area.getTargetRoomId();
		final float targetX = area.getTargetX();
		final float targetY = area.getTargetY();
		
		getWorld().switchRoom(targetRoomId, targetX, targetY);
	}
	
	public final void setSpeed(int value) {
		delay = 1000 / value;
	}
	
	public final int getSpeed() {
		return 1000 / delay;
	}
	
}































