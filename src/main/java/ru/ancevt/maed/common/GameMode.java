package ru.ancevt.maed.common;

import ru.ancevt.d2d2.common.D2D2;
import ru.ancevt.d2d2.display.Root;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.time.Timer;
import ru.ancevt.maed.gameobject.DynamicDoor;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.gui.Hint9;
import ru.ancevt.maed.inventory.Inventory;
import ru.ancevt.maed.inventory.InventoryView;
import ru.ancevt.maed.inventory.KeyType;
import ru.ancevt.maed.inventory.Pickup;
import ru.ancevt.maed.world.World;

public class GameMode  {
	
	private Root root;
	private World world;
	private UserActor userActor;
	private InventoryView inventoryView;
	private Hint9 keyHint;
	
	public GameMode(Root root, World world) {
		this.root = root;
		this.world = world;
		userActor = world.getUserActor();
		inventoryView = new InventoryView(userActor.getInventory());
		inventoryView.setXY(Viewport.WIDTH - inventoryView.getWidth(), 0);
		Game.rootLayer.add(inventoryView);
		
		//message(6, 5, "Hello world;");
	}
	
	public void onPuckUpPickup(Pickup pickup) {
		System.out.println("Picked up: " + pickup);
		inventoryView.update();
	}

	public void onUserActorSpawn(int roomId, float x, float y) {
		System.out.println("User actor spawn, room: " + roomId + ", x: " + x + ", y: " + y);
	}

	public void onUserActorDeath() {
		System.out.println("User actor death");
	}

	public void onRoomSwitched(int oldRoomId, int newRoomId) {
		System.out.println("Room switched from " + oldRoomId + " to " + newRoomId);
	}

	public void message(int w, int h, String text) {
		final Hint9 hint9 = new Hint9(w, h);
		hint9.setText(text);
		hint9.setXY(D2D2.getRenderer().getWidth()/2 - hint9.getWidth(), D2D2.getRenderer().getHeight()/2-hint9.getHeight());
		root.add(hint9);
	}

	public void onUserActorCollideDoor(DynamicDoor dynamicDoor) {
		if(!dynamicDoor.isOpen() && !userActor.getInventory().checkKeyTypeId(dynamicDoor)) {
			keyMessage(dynamicDoor.getKeyTypeId());
		} else {
			final Inventory inventory = userActor.getInventory();
			final int slot = inventory.keySlot(dynamicDoor.getKeyTypeId());
			if(slot != -1) {
				dynamicDoor.open();
				inventory.clearSlot(slot);
				Game.mode.onUserActorOpenDoor();
			}
		}
	}

	public void onUserActorOpenDoor() {
		inventoryViewUpdate();
	}
	
	public void inventoryViewUpdate() {
		inventoryView.update();
	}
	
	private final void keyMessage(int keyTypeId) {
		if(keyHint != null) return;
		
		keyHint = new Hint9(10, 3) {
			public void onTouch() {
				removeFromParent();
			};
		};
		root.add(keyHint);
		keyHint.setXY(D2D2.getRenderer().getWidth() / 2 - keyHint.getWidth(), 100);
		keyHint.setScale(2f, 2f);
		
		String word = null;
		String textureKey = null;
		
		switch (keyTypeId) {
			case KeyType.RED:
				word = "red";
				textureKey = "p_key_red";
				break;
			case KeyType.GREEN:
				word = "green";
				textureKey = "p_key_green";
				break;
			case KeyType.BLUE:
				word = "blue";
				textureKey = "p_key_blue";
				break;
			case KeyType.YELLOW:
				word = "yellow";
				textureKey = "p_key_yellow";
				break;
		}
		
		
		keyHint.setText(String.format("    Need %s key to \nunlock this door", word));
		final Sprite greenKey = new Sprite(textureKey);
		keyHint.add(greenKey, 12, 10);
		
		final Timer timer = new Timer(5000) {
			@Override
			public void onTimerTick() {
				keyHint.removeFromParent();
				keyHint = null;
				super.onTimerTick();
			}
		};
		timer.setLoop(false);
		timer.start();
	}

}











