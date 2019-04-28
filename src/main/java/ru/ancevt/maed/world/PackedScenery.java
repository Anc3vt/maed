package ru.ancevt.maed.world;

import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.display.texture.TextureAtlas;

public class PackedScenery extends Sprite {
	public PackedScenery(final TextureAtlas textureAtlas) {
		super(textureAtlas.createTexture());
	}
}
