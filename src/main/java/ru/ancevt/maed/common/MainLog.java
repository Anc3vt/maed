package ru.ancevt.maed.common;

import ru.ancevt.maed.editor.ShadowLabel;

public class MainLog extends ShadowLabel {
	private static MainLog instance;
	public static final MainLog getInstance() {
		return instance == null ? instance = new MainLog() : instance;
	}
	
	private MainLog() {
		
	}
	
	public final void log(Object o) {
		setText(getText() + '\n');
	}
}
