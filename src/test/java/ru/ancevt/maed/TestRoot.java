package ru.ancevt.maed;

import java.io.IOException;

import ru.ancevt.d2d2.debug.FPSMeter;
import ru.ancevt.d2d2.display.Root;
import ru.ancevt.d2d2.display.texture.TextureAtlas;
import ru.ancevt.d2d2.display.texture.TextureManager;
import ru.ancevt.maed.common.AnimationKey;
import ru.ancevt.maed.common.PlayerController;
import ru.ancevt.maed.gameobject.Actor_legacy;
import ru.ancevt.maed.gameobject.GameObjectFactory;
import ru.ancevt.maed.gameobject.Scenery;
import ru.ancevt.maed.gameobject.area.AreaCollision;
import ru.ancevt.maed.map.DataLine;
import ru.ancevt.maed.map.Mapkit;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.map.MapkitLoader;
import ru.ancevt.maed.world.World;

public class TestRoot extends Root {

	private static TestRoot instance;
	
	public static final TestRoot getInstance() throws IOException {
		return instance == null ? instance = new TestRoot() : instance;
	}
	
	public TestRoot() throws IOException {
		
		final TextureAtlas mainCharTextureAtlas 
			= TextureManager.getInstance().loadTextureAtlas("mapkit/defaultmapkit/mainchar-tileset.png");
		
		final Mapkit mapkit1 = new Mapkit();
		
		mapkit1.setTextureAtlas(mainCharTextureAtlas);
		final MapkitItem item = mapkit1.createItem(avaDataLine());

		Actor_legacy ava = new Actor_legacy(item, -1) {
			@Override
			public void onEachFrame() {
				System.out.println(this.getFloor());
				super.onEachFrame();
			}
		};
		GameObjectFactory.setUpGameObject(ava, avaDataLine());
		ava.setController(PlayerController.getInstance());
		ava.setHealth(100);
		ava.setAnimation(AnimationKey.IDLE, true);
		ava.setController(PlayerController.getInstance());
		ava.setXY(32, 32);
		ava.setGravityEnabled(true);
		
		System.out.println(GameObjectFactory.gameObjectToDataLineString(ava, 5));
		
		World world = new World();
		world.setPlayMode(true);
		
		Mapkit mapkit = MapkitLoader.load("mapkit1/");
		
		Scenery sky = (Scenery) GameObjectFactory.createGameObjectFromMapFile(mapkit.getItemByName("sky0"), DataLine.parse("1"));
		sky.setRepeatX(10);
		world.addGameObject(sky, 0, false);
		
		Scenery gnd = (Scenery) GameObjectFactory.createGameObjectFromMapFile(mapkit.getItemByName("grd_t2"), DataLine.parse("2"));
		gnd.setY(sky.getHeight());
		gnd.setRepeatX(10);
		world.addGameObject(gnd, 0, false);
		
		AreaCollision floor = (AreaCollision) GameObjectFactory.createGameObjectFromMapFile(mapkit.getItemById(10010), DataLine.parse("3"));
		floor.setSize(16*10, 16);
		floor.setY(sky.getHeight());
		world.addGameObject(floor, 9, false);
		
		world.addGameObject(ava, 5, false);
		
		
		world.getLayer(9).add(new FPSMeter());
		add(world);
		world.setScale(4f, 4f);
	}
	
	private static final DataLine avaDataLine() {
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
        s.append("jump-power = 2.1 |");
        s.append("weapon-pos-x = 24.0 |");
        s.append("weapon-pos-y = -5.0 |");
        s.append("start-x = 128 | start-y = 128 |");

        return DataLine.parse(s.toString());
    }
	
}
