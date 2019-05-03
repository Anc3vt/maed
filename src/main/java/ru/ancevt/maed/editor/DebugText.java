package ru.ancevt.maed.editor;

public class DebugText extends ShadowLabel {
	private static DebugText instance;
	public static final DebugText getIntstance() {
		return instance == null ? instance = new DebugText() : instance;
	}
}
