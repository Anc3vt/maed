package ru.ancevt.maed.common;

import ru.ancevt.maed.gameobject.IDirectioned;

public class Direction {
	
	public static final int RIGHT = 0x01;
	public static final int LEFT  = 0x02;
	public static final int DOWN  = 0x04;
	public static final int UP    = 0x08;
	
	public static final boolean check(final IDirectioned target, final int check) {
		final int value = target.getDirection();
		return (value & check) != 0;
	}
	
	
}