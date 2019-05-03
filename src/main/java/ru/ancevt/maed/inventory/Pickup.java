package ru.ancevt.maed.inventory;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.common.Game;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.IResettable;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.map.MapkitItem;

public class Pickup extends DisplayObjectContainer implements IGameObject, ICollisioned,
IResettable{
	private Sprite frame;
	private Sprite icon;
	private int id;
	private MapkitItem mapkitItem;
	private boolean pickedUp;
	private InventoryItem inventoryItem;
	
	public Pickup(MapkitItem mapkitItem, int gameObjectId) {
		frame = new Sprite("pickup_frame");
		this.mapkitItem = mapkitItem;
		id = gameObjectId;
		
		add(frame);
		icon = new Sprite();
		add(icon);
	}
	
	public final void setIcon(String iconTextureKey) {
		icon.setTexture(iconTextureKey);
	}
	
	@Override
	public void setCollisionEnabled(boolean value) {
		// has no effect
	}

	@Override
	public boolean isCollisionEnabled() {
		return !isPickedUp();
	}

	@Override
	public void setCollisionArea(float x, float y, float w, float h) {
		
	}

	@Override
	public float getCollisionX() {
		return 0;
	}

	@Override
	public float getCollisionY() {
		return 0;
	}

	@Override
	public float getCollisionWidth() {
		return 16;
	}

	@Override
	public float getCollisionHeight() {
		return 16;
	}

	@Override
	public void setCollisionVisible(boolean b) {
	}

	@Override
	public boolean isCollisionVisible() {
		return false;
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		if(isPickedUp()) return;
		if(collideWith instanceof UserActor) {
			final UserActor userActor = (UserActor)collideWith;
			setPickedUp(true);
			onUserPickup(userActor);
			
		}
	}
	
	public void onUserPickup(UserActor userActor) {
		Game.gameListener.onPuckUpPickup(this);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean isSaveable() {
		return true;
	}

	@Override
	public MapkitItem getMapkitItem() {
		return mapkitItem;
	}

	@Override
	public IGameObject copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
	
	public final void setPickedUp(boolean value) {
		pickedUp = value;
		setVisible(!value);
	}
	
	public final boolean isPickedUp() {
		return pickedUp;
	}

	@Override
	public void reset() {
		setPickedUp(false);
	}

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(InventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}
	
}
