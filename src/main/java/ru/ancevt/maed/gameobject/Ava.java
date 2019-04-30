package ru.ancevt.maed.gameobject;

import ru.ancevt.d2d2.display.texture.TextureAtlas;
import ru.ancevt.d2d2.display.texture.TextureManager;
import ru.ancevt.maed.common.AKey;
import ru.ancevt.maed.common.PlayerController;
import ru.ancevt.maed.gameobject.weapon.DefaultWeapon;
import ru.ancevt.maed.gameobject.weapon.Weapon;
import ru.ancevt.maed.map.DataLine;
import ru.ancevt.maed.map.Mapkit;
import ru.ancevt.maed.map.MapkitItem;

public class Ava extends UserActor {
	
	private static Mapkit mapkit;
	private static MapkitItem mapkitItem;
	private static DataLine dataLine;
	
	public Ava() {
		super(createMapkitItemIfNotExists(), -1);
		GameObjectFactory.setUpGameObject(this, createDataLine());
		setController(PlayerController.getInstance());
		setAnimation(AKey.IDLE, true);
		setController(PlayerController.getInstance());
		setGravityEnabled(true);
		
		final Weapon weapon = new DefaultWeapon();
		setWeapon(weapon);
	}
	
	private static MapkitItem createMapkitItemIfNotExists() {
		if(mapkit == null) {
			final TextureAtlas mainCharTextureAtlas = TextureManager.getInstance()
					.loadTextureAtlas("mapkit/defaultmapkit/mainchar-tileset.png");
	
			mapkit = new Mapkit("defaultmapkit/");
			mapkit.setTextureAtlas(mainCharTextureAtlas);
		}
		
		if(mapkitItem == null) {
			mapkitItem = mapkit.createItem(createDataLine());
		}
		
		return mapkitItem;
	}

	private static final DataLine createDataLine() {
		if(dataLine == null) {
	        final StringBuilder s = new StringBuilder();
	        s.append("1 | 3 | Ava | ");
	        s.append("idle = 0,192,48,48; 48,192,48,48 |");
	        s.append("attack = 144,192,48,48; 96,336,48,48 |");
	        s.append("walk = 192,192,48,48; 240,192,48,48; 288,192,48,48; 336,192,48,48 |");
	        s.append("walk-attack = 0,240,48,48; 48,240,48,48; 96,240,48,48; 144,240,48,48 |");
	        s.append("jump = 192,240,48,48 |");
	        s.append("fall = 240,240,48,48 |");
	        s.append("jump-attack = 288,240,48,48 |");
	        s.append("fall-attack = 336,240,48,48 |");
	        s.append("hook = 240,288,48,48 |");
	        s.append("hook-attack = 336,288,48,48 |");
	        s.append("damage = 48,336,48,48 |");
	        
	        s.append("collision-area = -8,-16,16,32 |");
	        s.append("weight = 0.3 |");
	        s.append("speed = 0.7 |");
	        s.append("max-health = 50 |");
	        s.append("jump-power = 2.0 |");
	        s.append("weapon-pos-x = 14.0 |");
	        s.append("weapon-pos-y = -5.0 |");
	        s.append("start-x = 128 | start-y = 128 |");
	        s.append("snd-jump=jump.mp3|snd-damage=pain.mp3");

	        dataLine = DataLine.parse(s.toString());
		}
		return dataLine;
    }
}
