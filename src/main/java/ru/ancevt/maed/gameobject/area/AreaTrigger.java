package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class AreaTrigger extends Area {

	private static final Color COLOR_FILL = Color.MAGENTA;
	private static final Color COLOR_STROKE = Color.WHITE;

	private String triggerOptions; 

	public AreaTrigger(final MapkitItem mapKitItem, final int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);
	}

	@Override
	public int getAreaType() {
		return Area.TRIGGER;
	}

	@Override
	public Area copy() {
		final AreaTrigger result = new AreaTrigger(getMapkitItem(), World.getWorld().getMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		result.setTriggerOptions(getTriggerOptions());
		return result;
	}

	public String getTriggerOptions() {
		return triggerOptions;
	}

	public void setTriggerOptions(String options) {
		checkDublicate(options);
		
		this.triggerOptions = options;
		setText(options);
	}
	
	public final boolean isJump() {
		return triggerOptions.contains("J");
	}
	
	public final boolean isLeft() {
		return triggerOptions.contains("<");
	}

	public final boolean isRight() {
		return triggerOptions.contains(">");
	}
	
	public final boolean isStop() {
		return triggerOptions.contains("S");
	}
	
	private static final void checkDublicate(String test) {
		if(test == null) return;
		
		final String pattern = "<>JS";

		for (int i = 0; i < pattern.length(); i ++) {
			final char c  = pattern.charAt(i);
			int count = 0;
			
			for(int j = 0 ; j < test.length(); j ++) {
				final char c0 =test.charAt(j);
				if(c == c0) count ++;
			}
			
			if(count > 1) throw new RuntimeException("invalid areapattern " + test);
		}
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
}
