package ru.ancevt.maed.common;

public abstract class AnimationKey {
	public static final int MAX_ANIMATIONS 	= 13;
	public static final int SLOWING 		= 10;
	
	public static final int IDLE         	= 0;
	public static final int WALK         	= 1;
	public static final int ATTACK       	= 2;
	public static final int JUMP         	= 3;
	public static final int JUMP_ATTACK  	= 4;
	public static final int WALK_ATTACK  	= 5;
	public static final int DAMAGE       	= 6;
	public static final int DEFENSE      	= 7;
	public static final int HOOK         	= 8;
	public static final int HOOK_ATTACK  	= 9;
	public static final int FALL         	= 10;
	public static final int FALL_ATTACK  	= 11;
	public static final int DEATH			= 12;
	public static final int EXTRA_ANIMATION = 13;
}
