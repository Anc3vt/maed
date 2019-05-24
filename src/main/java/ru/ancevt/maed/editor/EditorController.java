package ru.ancevt.maed.editor;

import java.util.LinkedList;
import java.util.List;

import ru.ancevt.d2d2.time.Timer;
import ru.ancevt.d2d2.time.TimerListener;

public class EditorController implements TimerListener {
	
	private static EditorController instance;
	public static final EditorController getInstance() {
		return instance == null ? instance = new EditorController() : instance;
	}
	
	private EditorControllerListener listener;
	
	private Timer timer;
	private List<Integer> down;
	private List<Integer> up;
	private boolean shift;
	private boolean control;
	private boolean alt;
	
	private EditorController() {
		down = new LinkedList<Integer>();
		up = new LinkedList<Integer>();
		
		timer = new Timer(1);
		timer.setLoop(true);
		timer.setListener(this);
	}
	
	public void putDown(int keyCode) {
		down.add(keyCode);
	}
	
	public void putUp(int keyCode) {
		up.add(keyCode);
	}
	
	public void setShift(boolean shift) {
		this.shift = shift;
	}
	
	public boolean isShift() {
		return shift;
	}
	
	public void setControl(boolean control) {
		this.control = control;
	}
	
	public boolean isControl() {
		return control;
	}
	
	public void setAlt(boolean alt) {
		this.alt = alt;
	}
	
	public boolean isAlt() {
		return alt;
	}
	
	public void start() {
		timer.start();
	}
	
	public boolean isStarted() {
		return timer.isStarted();
	}
	
	public void stop() {
		timer.stop();
	}

	@Override
	public void onTimerTick() {
		while(!down.isEmpty())
			dispatchKeyDown(down.remove(0), shift, control, alt);
		
		while(!up.isEmpty()) 
			dispatchKeyUp(up.remove(0), shift, control, alt);
	}
	
	private void dispatchKeyDown(int keyCode, boolean shift, boolean control, boolean alt) {
		if(listener != null) listener.editorControllerKeyDown(keyCode, shift, control, alt);
	}
	
	private void dispatchKeyUp(int keyCode, boolean shift, boolean control, boolean alt) {
		if(listener != null) listener.editorControllerKeyUp(keyCode, shift, control, alt);
	}

	public EditorControllerListener getListener() {
		return listener;
	}

	public void setListener(EditorControllerListener listener) {
		this.listener = listener;
	}
}
