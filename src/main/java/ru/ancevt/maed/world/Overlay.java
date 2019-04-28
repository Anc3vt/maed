package ru.ancevt.maed.world;

import ru.ancevt.d2d2.common.PlainRect;
import ru.ancevt.d2d2.display.Color;

public class Overlay extends PlainRect {
	
	private static final Color COLOR = Color.BLACK;
	public static final int STATE_IN    = 0;
	public static final int STATE_BLACK = 1;
	public static final int STATE_OUT   = 2;
	public static final int STATE_DONE  = 3;
	
	private static final float ALPHA_SPEED = 0.1f;
	
	private int state;
	private float alpha;
	
	public Overlay(int width, int height) {
		setColor(COLOR);
		setSize(width, height);
		setAlpha(0f);
	}

	public final void startIn() {
		state = STATE_IN;
		alpha = 0.0f;
	}
	
	public final void startOut() {
		state = STATE_OUT;
		alpha = 1.0f;
	}
	
	public final int getState() {
		return state;
	}

	public final void setState(final int state) {
		this.state = state;
		onStateChanged(state);
	}
	
	@Override
	public void onEachFrame() {
	
		switch (getState()) {
			case STATE_IN:
				alpha += ALPHA_SPEED;
				setAlpha(alpha <= 1.0f ? alpha : 1.0f);
				if(alpha >= 1.2f) setState(STATE_BLACK);
				break;
				
			case STATE_OUT:
				alpha -= ALPHA_SPEED;
				setAlpha(alpha >= 0.0f ? alpha : 0.0f);
				if(alpha <= -0.2f) setState(STATE_DONE);
				break;
		}
		
		super.onEachFrame();
	}
	
	public void onStateChanged(final int state) {
		
	}

}
