package ru.ancevt.maed.world;

import ru.ancevt.d2d2.display.texture.Texture;
import ru.ancevt.d2d2.display.texture.TextureAtlas;
import ru.ancevt.d2d2.display.texture.TextureCombiner;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.Scenery;
import ru.ancevt.maed.map.Room;

public class SceneryPacker {
	public static final PackedScenery pack(final Room room, final int layerFrom, final int layerTo) {
		
		final TextureCombiner comb = new TextureCombiner(room.getWidth(), room.getHeight());
		
		for(int layer = layerFrom; layer <= layerTo; layer ++) {
			for(int i = 0; i < room.getGameObjectsCount(layer); i ++) {
				final IGameObject o = room.getGameObject(layer, i);
				if(o instanceof Scenery) {
					final Scenery s = (Scenery)o;
					final Texture textureRegion = s.getTexture();
					final int x = (int)s.getX();
					final int y = (int)s.getY();
					final float scaleX = s.getScaleX();
					final float scaleY = s.getScaleY();
					final float alpha = s.getAlpha();
					final float rotation = s.getRotation();
					final int repeatX = s.getRepeatX();
					final int repeatY = s.getRepeatY();
					
					comb.append(textureRegion, x, y, scaleX, scaleY, alpha, rotation, repeatX, repeatY);
				}
			}
		}
		
		final TextureAtlas textureAtlas = comb.createTextureAtlas();
		final PackedScenery result = new PackedScenery(textureAtlas);
		
		return result;
	}
}
