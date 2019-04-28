package ru.ancevt.maed.gameobject.action;

import ru.ancevt.d2d2.display.DisplayObject;
import ru.ancevt.maed.common.AnimationKey;
import ru.ancevt.maed.common.Controller;
import ru.ancevt.maed.common.Direction;
import ru.ancevt.maed.gameobject.IActioned;
import ru.ancevt.maed.gameobject.IAlphable;
import ru.ancevt.maed.gameobject.IAnimated;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IControllable;
import ru.ancevt.maed.gameobject.IDamaging;
import ru.ancevt.maed.gameobject.IDestroyable;
import ru.ancevt.maed.gameobject.IGravitied;
import ru.ancevt.maed.gameobject.IMoveable;
import ru.ancevt.maed.gameobject.IResettable;
import ru.ancevt.maed.gameobject.IRotatable;
import ru.ancevt.maed.gameobject.ITight;
import ru.ancevt.maed.gameobject.Bot;
import ru.ancevt.maed.gameobject.Actor;

public class ActionProcessor {
	
	private static final String TRUE = "TRUE";
	
	public static final void process(final IActioned o) {
		final ActionProgram program = o.getActionProgram();
		
		if(program == null || program.isEnded() || program.isEmpty()) return;
		
		final ActionStatement s = program.getCurrentStatement();
		
		if(program.isWalking()) {
			final Actor c = (Actor)o;
			if(Direction.check(c, Direction.LEFT))
				c.getController().setLeft(true);
			else
				c.getController().setRight(true);
		}
		
		if(program.isMoving()) {
			final float speedX = program.getSpeedX();
			final float speedY = program.getSpeedY();
			
			o.move(speedX, speedY);
		}
		
		if(program.getPauseDelay() > 0) {
			program.setPauseDelay(program.getPauseDelay() - 1);
			
			return;
		}
		
		if(s == null) return;
		
		
		Controller c = (o instanceof IControllable) ? ((IControllable)o).getController() : null;
		
		
		final int key = s.getKey();
		
		switch (key) {
			case ActionStatement.MOVE:
				o.move(s.getFloat(0), s.getFloat(1));
				break;
	
			case ActionStatement.PAUSE:
				program.setPauseDelay(s.getInt(0));
				break;
				
			case ActionStatement.RESET:
				((IResettable)o).reset();
				break;
			
			case ActionStatement.SETSPEED:
				((IMoveable)o).setSpeed(s.getFloat(0));
				break;
				
			case ActionStatement.LOOP:
				program.goToLoopBegin();
				break;
				
			case ActionStatement.MOVESTART:
				program.setSpeedX(s.getFloat(0));
				program.setSpeedY(s.getFloat(1));
				program.setMoving(true);
				break;
				
			case ActionStatement.MOVESTOP:
				program.setMoving(false);
				break;
			
			case ActionStatement.SETCOLLRECT:
				((ICollisioned)o).setCollisionArea(
					s.getFloat(0), s.getFloat(1),
					s.getFloat(2), s.getFloat(3)
				);
				break;
				
			case ActionStatement.ADDHEALTH:
				final IDestroyable d = (IDestroyable)o;
				d.addHealth(s.getInt(0));
				break;
				
			case ActionStatement.SETDAMPOWER:
				((IDamaging)o).setDamagingPower(s.getInt(0));
				break;
				
			case ActionStatement.SETHEALTH:
				((IDestroyable)o).setHealth(s.getInt(0));
				break;
				
			case ActionStatement.SETMAXHEALTH:
				((IDestroyable)o).setMaxHealth(s.getInt(0));
				break;
				
			case ActionStatement.EXTRAFRAME:
				o.getMapkitItem().getTexture(AnimationKey.EXTRA_ANIMATION, s.getInt(0));
				break;
				
			case ActionStatement.PLAYSOUND:
				//TODO: sounds
				//o.getMapKitItem().playSound(s.getInt(0));
				break;
				
			case ActionStatement.SETALPHA:
				((IAlphable)o).setAlpha(s.getFloat(0));
				break;
				
			case ActionStatement.ROTATE:
				((IRotatable)o).rotate(s.getFloat(0));
				break;
				
			case ActionStatement.SETROTATION:
				((IRotatable)o).setRotation(s.getFloat(0));
				break;
				
			case ActionStatement.SETCOLLENABLED:
				((ICollisioned)o).setCollisionEnabled(TRUE.equals(s.getString(0)));
				break;
				
			case ActionStatement.SETFLOORONLY:
				((ITight)o).setFloorOnly(TRUE.equals(s.getString(0)));
				break;
				
			case ActionStatement.SETPUSHABLE:
				((ITight)o).setPushable(TRUE.equals(s.getString(0)));
				break;
				
			case ActionStatement.SETWEIGHT:
				((IGravitied)o).setWeight(s.getFloat(0));
				break;
				
			case ActionStatement.SETGRAVITYENABLED:
				((IGravitied)o).setGravityEnabled(TRUE.equals(s.getString(0)));
				break;
				
			case ActionStatement.PLAYANIMATION:
				final int animationKey = s.getInt(0);
				final boolean loop = s.getString(1).equals(TRUE);
				((IAnimated)o).setAnimation(animationKey, loop);
				break;
				
			case ActionStatement.SETVISIBLE:
				((DisplayObject)o).setVisible(TRUE.equals(s.getString(0)));
				break;
				
			case ActionStatement.CONTROLLER:
				if(o instanceof IControllable) {
					
					final String button = s.getString(0);
					final char ch = button.charAt(0);
					
					final boolean pushed = TRUE.equals(s.getString(1));
					
					switch (ch) {
						case 'C': c.setC(pushed); break;
						case 'B': c.setB(pushed); break;
						case 'A': c.setA(pushed); break;
						case 'L': c.setLeft(pushed); break;
						case 'R': c.setRight(pushed);break;
						case 'U': c.setUp(pushed); break;
						case 'D': c.setDown(pushed); break;
					}
					
				}
				break;
				
			case ActionStatement.SETREACTSONMARKERS:
				((Bot)o).setReactsOnMarkers(TRUE.equals(s.getString(0)));
				break;
				
			case ActionStatement.SETFACETOFACE:
				((Bot)o).setAlwaysFaceToface(TRUE.equals(s.getString(0)));
				
				break;
				
			case ActionStatement.WALKSTART:
				program.setWalking(true);
				break;
				
			case ActionStatement.WALKSTOP:
				program.setWalking(false);
				
				c.setLeft(false);
				c.setRight(false);
				
				break;
				
			case ActionStatement.WALKLEFT:
				c.setRight(false);
				c.setLeft(true);
				
				break;
				
			case ActionStatement.WALKRIGHT:
				c.setLeft(false);
				c.setRight(true);
				
				break;
				
				
		}
		
		program.nextStatement();
		process(o);
	}
}
/*
 * 
SETCOLLRECT 	= 10,
SETDAMPOWER 	= 13,
SETHP 			= 14,
SETMAXHP 		= 15,
EXTRAFRAME 		= 16,
PLAYSOUND 		= 17,
SETALPHA 		= 18,
ROTATE 			= 19,
SETROTATION 	= 20,
SETCOLLENABLED 	= 21,
SETFLOORONLY 	= 22,
SETPUSHABLE 	= 23,
SETWEIGHT 		= 24,
SETGRAVITYENABLED = 25,
PLAYANIMATION 	= 26,
SETVISIBLE 		= 27,
		
		*/
