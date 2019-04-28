package ru.ancevt.maed.map;

import ru.ancevt.d2d2.display.texture.Texture;
import ru.ancevt.d2d2.display.texture.TextureAtlas;
import ru.ancevt.d2d2.display.texture.TextureManager;
import ru.ancevt.d2d2.sound.Sound;
import ru.ancevt.maed.common.AnimationKey;
import ru.ancevt.maed.common.DataKey;
import ru.ancevt.maed.common.Path;
import ru.ancevt.maed.common.SoundKey;
import ru.ancevt.maed.common.TilesetZone;
import ru.ancevt.maed.gameobject.area.Area;

public class MapkitItem {
	
	public static class Category {
        public static final byte ALL        = 0;
        public static final byte AREAS      = 1;
        public static final byte SCENERY    = 2;
        public static final byte ACTOR  = 3;
        public static final byte DYNAMIC    = 4;
        public static final byte ANIMATED_SCENERY = 5;

        private static final String NAME_UNKNOWN    = "Unknown";
        private static final String NAME_ALL        = "All";
        private static final String NAME_AREAS      = "Areas";
        private static final String NAME_SCENERY    = "Scenery";
        private static final String NAME_CHARACTERS = "Actors";
        private static final String NAME_DYNAMIC    = "Dynamic";
        private static final String NAME_ANIMATED_SCENERY = "Animated scenery";

        public static final String getName(final int category) {
            switch (category) {
                case ALL:       return NAME_ALL;
                case AREAS:     return NAME_AREAS;
                case SCENERY:   return NAME_SCENERY;
                case ACTOR: return NAME_CHARACTERS;
                case DYNAMIC:   return NAME_DYNAMIC;
                case ANIMATED_SCENERY: return NAME_ANIMATED_SCENERY;

                default: return NAME_UNKNOWN;
            }
        }

        public static final boolean isUnknown(final int category) {
            return NAME_UNKNOWN.equals(getName(category));
        }
    }
	
    private static final int MAX_TEXTURE_TYPES = 16;
	
	private static int DATALINE_INDEX_ID = 0;
    private static int DATALINE_INDEX_CATEGORY = 1;
    private static int DATALINE_INDEX_NAME = 2;

	private Mapkit mapkit;
	private DataLine dataLine;
	
	private Texture[][] textures;
    private Sound[][] sounds;
	
	MapkitItem(Mapkit mapkit, DataLine dataLine) {
		this.mapkit = mapkit;
		this.dataLine = dataLine;
		
		textures = prepareTextures();
		sounds = prepareSounds();
	}
		
	public final int getId() {
		return dataLine.getInt(DATALINE_INDEX_ID);
	}
	
	public final int getCategory() {
		return dataLine.getInt(DATALINE_INDEX_CATEGORY);
	}
	
	public final String getName() {
		return dataLine.getString(DATALINE_INDEX_NAME);
	}
	
	public final int getTextureCount(final int animationKey) {
        return textures[animationKey].length;
    }

    public final boolean isAnimationKeyExists(final int animationKey) {
        return textures[animationKey] != null;
    }
    
    public final Texture getTexture() {
		if (getCategory() == Category.AREAS) {

            final TextureManager tm = TextureManager.getInstance();

            final int areaType = dataLine.getInt(DataKey.AREA_TYPE);

            switch (areaType) {
                case Area.COLLISION:
                    return tm.getTexture("area_collision");
                case Area.DAMAGING:
                    return tm.getTexture("area_damaging");
                case Area.CHECKPOINT:
                    return tm.getTexture("area_checkpoint");
                case Area.DOOR_TELEPORT:
                    return tm.getTexture("area_door_teleport");
                case Area.HOOK:
                    return tm.getTexture("area_hook");
                case Area.TRIGGER:
                	return tm.getTexture("area_trigger");

                default:
                    return null;
            }

        }
		
		final TextureAtlas textureAtlas = mapkit.getTextureAtlas();
		final TilesetZone tilesetZone = dataLine.getTilesetZone(DataKey.IDLE);
		final Texture iconTexture = textureAtlas.createTexture(
			tilesetZone.getX(),
			tilesetZone.getY(),
			tilesetZone.getWidth(),
			tilesetZone.getHeight()
		);
		return iconTexture;
	}


    public final Texture getTexture(final int animationKey, int frameIndex) {
        return textures[animationKey][frameIndex];
    }
    
    private final Texture[][] prepareTextures() {
        final Texture[][] result = new Texture[MAX_TEXTURE_TYPES][];

        result[AnimationKey.IDLE] = prepareTextureRegionsOfKey(DataKey.IDLE);
        result[AnimationKey.WALK] = prepareTextureRegionsOfKey(DataKey.WALK);
        result[AnimationKey.ATTACK] = prepareTextureRegionsOfKey(DataKey.ATTACK);
        result[AnimationKey.JUMP] = prepareTextureRegionsOfKey(DataKey.JUMP);
        result[AnimationKey.JUMP_ATTACK] = prepareTextureRegionsOfKey(DataKey.JUMP_ATTACK);
        result[AnimationKey.WALK_ATTACK] = prepareTextureRegionsOfKey(DataKey.WALK_ATTACK);
        result[AnimationKey.DAMAGE] = prepareTextureRegionsOfKey(DataKey.DAMAGE);
        result[AnimationKey.DEFENSE] = prepareTextureRegionsOfKey(DataKey.DEFENSE);
        result[AnimationKey.HOOK] = prepareTextureRegionsOfKey(DataKey.HOOK);
        result[AnimationKey.HOOK_ATTACK] = prepareTextureRegionsOfKey(DataKey.HOOK_ATTACK);
        result[AnimationKey.FALL] = prepareTextureRegionsOfKey(DataKey.FALL);
        result[AnimationKey.FALL_ATTACK] = prepareTextureRegionsOfKey(DataKey.FALL_ATTACK);
        result[AnimationKey.EXTRA_ANIMATION] = prepareTextureRegionsOfKey(DataKey.EXTRA_ANIMATION);

        return result;
    }

    private final Texture[] prepareTextureRegionsOfKey(final String key) {

        final TilesetZone[] tilesetZones = dataLine.getTilesetZones(key);

        if(tilesetZones == null) return null;
        
        final Texture[] result = new Texture[tilesetZones.length];

        for (int i = 0; i < tilesetZones.length; i++) {
            final TilesetZone tz = tilesetZones[i];
            
            final TextureAtlas atlas = mapkit.getTextureAtlas();
            if(atlas != null) {
	        	result[i] = mapkit.getTextureAtlas().createTexture(
	            	tz.getX(), tz.getY(), tz.getWidth(), tz.getHeight()
	            );
            }
        }

        return result;
    }

    private final Sound[][] prepareSounds() {
        final Sound[][] result = new Sound[SoundKey.MAX_SOUNDS][];

        result[SoundKey.JUMP] = prepareSoundsOfKey(DataKey.SOUND_JUMP);
        result[SoundKey.DAMAGE] = prepareSoundsOfKey(DataKey.SOUND_DAMAGE);
        result[SoundKey.EXTRA_SOUND] = prepareSoundsOfKey(DataKey.SOUND_EXTRA);
        result[SoundKey.DEATH] = prepareSoundsOfKey(DataKey.SOUND_DEATH);

        return result;
    }

    private final Sound[] prepareSoundsOfKey(final String key) {
        final String value = dataLine.getString(key);

        if(value == null) return null;

        final String[] valueStrings = value.split(";");

        final Sound[] result = new Sound[valueStrings.length];

        for(int i = 0; i < valueStrings.length; i ++) {
            final String current = valueStrings[i];

            final String path =
                Path.MAPKIT_DIRECTORY + mapkit.getDirectory() + current;

            final Sound sound = new Sound(path);
            result[i] = sound;
        }

        return result;
    }
    
    public final void playSound(final int soundKey) {
        playSound(soundKey, 0);
    }

    public final void playSound(final int soundKey, final int index) {
        if(sounds[soundKey] == null || sounds[soundKey].length <= index ||sounds[soundKey][index] == null) {
            System.out.println("warning: no such sound " + this + " [" + soundKey + "][" + index + "]");
            return;
        }

        sounds[soundKey][index].play();
    }



	
	public final DataLine getDataLine() {
		return dataLine;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + dataLine.stringify() + "]";
	}
}













