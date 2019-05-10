package ru.ancevt.maed.common;

import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.time.Timer;
import ru.ancevt.maed.gameobject.DynamicDoor;
import ru.ancevt.maed.gameobject.UserActor;
import ru.ancevt.maed.gameobject.area.AreaCheckpoint;
import ru.ancevt.maed.gui.GameGUI;
import ru.ancevt.maed.gui.Hint9;
import ru.ancevt.maed.inventory.Inventory;
import ru.ancevt.maed.inventory.InventoryView;
import ru.ancevt.maed.inventory.KeyType;
import ru.ancevt.maed.inventory.Pickup;
import ru.ancevt.maed.map.Room;
import ru.ancevt.maed.world.World;

public class GameMode  {
	
	private World world;
	private UserActor userActor;
	private Hint9 keyHint;
	private GameGUI gameGui;
	
	public GameMode(World world, GameGUI gameGui) {
		this.world = world;
		this.gameGui = gameGui;
		userActor = world.getUserActor();
		
		//message(6, 5, "Hello world;");
	}
	
	public void onPuckUpPickup(Pickup pickup) {
		System.out.println("Picked up: " + pickup);
		gameGui.updateInventory();
	}

	public void onUserActorSpawn(int roomId, float x, float y) {
		System.out.println("User actor spawn, room: " + roomId + ", x: " + x + ", y: " + y);
	}

	public void onUserActorDeath() {
		System.out.println("User actor death");
		
		final Hint9 hint = message(6, 2, "You are dead");
		final Timer t = new Timer(5000) {
			public void onTimerTick() {
				userActor.reset();
				
				final AreaCheckpoint lastContinueCheckpoint = userActor.getLastContinueCheckPoint();
				final Room room = world.getMap().getRoomByGameObject(lastContinueCheckpoint);
				
				world.switchRoom(room.getId(), lastContinueCheckpoint.getX(), lastContinueCheckpoint.getY());
				userActor.reset();
				hint.removeFromParent();
			};
		};
		t.setLoop(false);
		t.start();
	}

	public void onRoomSwitched(int oldRoomId, int newRoomId) {
		System.out.println("Room switched from " + oldRoomId + " to " + newRoomId);
	}

	public Hint9 message(int w, int h, String text) {
		final Hint9 hint9 = new Hint9(w, h);
		hint9.setText(text);
		hint9.setXY(Viewport.WIDTH/2 - hint9.getWidth(), Viewport.HEIGHT/2-hint9.getHeight());
		Game.rootLayer.add(hint9);
		return hint9;
	}

	public void onUserActorCollideDoor(DynamicDoor dynamicDoor) {
		if(!dynamicDoor.isOpen() && dynamicDoor.getKeyTypeId() == 0) {
			dynamicDoor.open();
			return;
		}

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
		gameGui.updateInventory();
	}
	
	private final void keyMessage(int keyTypeId) {
		if(keyHint != null) return;
		
		keyHint = new Hint9(10, 3) {
			public void onTouch() {
				removeFromParent();
			};
		};
		Game.rootLayer.add(keyHint);
		keyHint.setXY(Viewport.WIDTH / 2 - keyHint.getWidth(), Viewport.HEIGHT / 2 - keyHint.getHeight());
		
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

	public void onUserActorCollideCheckpoint(AreaCheckpoint cp) {
		if(cp.getCheckPointType() == AreaCheckpoint.CHECKPOINT_TYPE_CONTINUE) {
			userActor.setLastContinueCheckPoint(cp);
		}
	}

	public void onUserActorMoneyChange(int money) {
		gameGui.setMoney(money);
	}

	public void onUserActorHealthChanged(int health) {
		gameGui.setHealth(health);
	}

}











