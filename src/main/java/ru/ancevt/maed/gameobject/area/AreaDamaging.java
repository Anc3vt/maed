package ru.ancevt.maed.gameobject.area;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.maed.gameobject.Actor;
import ru.ancevt.maed.gameobject.IDamaging;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;

public class AreaDamaging extends Area implements IDamaging {

	private static final Color COLOR_FILL = Color.DARK_RED;
	private static final Color COLOR_STROKE = Color.RED;
	
	public static final int DEFAULT_DAMAGING_POWER = 20;

	private int damagingPower;

	public AreaDamaging(final MapkitItem mapKitItem, final int gameObjectId) {
		super(mapKitItem, gameObjectId);
		setBorderColor(COLOR_STROKE);
		setFillColor(COLOR_FILL);
		setDamagingPower(DEFAULT_DAMAGING_POWER);
		
		bitmapText.setColor(Color.RED);
	}

	@Override
	public int getAreaType() {
		return Area.DAMAGING;
	}

	@Override
	public final void setDamagingPower(final int damagingPower) {
		this.damagingPower = damagingPower;
		setText(damagingPower + new String());
	}

	@Override
	public final int getDamagingPower() {
		return this.damagingPower;
	}

	@Override
	public Area copy() {
		final AreaDamaging result = new AreaDamaging(getMapkitItem(), 
				World.getWorld().getMap().getNextFreeGameObjectId());
		result.setXY(getX(), getY());
		result.setSize(getWidth(), getHeight());
		result.setDamagingPower(getDamagingPower());
		return result;
	}

	@Override
	public void setDamagingOwnerActor(Actor character) {}

	@Override
	public Actor getDamagingOwnerActor() {
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + getId();
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
}
