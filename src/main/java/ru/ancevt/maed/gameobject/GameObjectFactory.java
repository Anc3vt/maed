package ru.ancevt.maed.gameobject;

import ru.ancevt.maed.common.DataKey;
import ru.ancevt.maed.gameobject.actionprogram.ActionProgram;
import ru.ancevt.maed.gameobject.area.Area;
import ru.ancevt.maed.gameobject.area.AreaCheckpoint;
import ru.ancevt.maed.gameobject.area.AreaDoorTeleport;
import ru.ancevt.maed.gameobject.area.AreaDoorTeleportCp;
import ru.ancevt.maed.gameobject.area.AreaTrigger;
import ru.ancevt.maed.gameobject.area.AreaWater;
import ru.ancevt.maed.gameobject.area.AreaWind;
import ru.ancevt.maed.map.DataLine;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.map.MapkitItem.Category;

public class GameObjectFactory {
	
	public static final float DEFAULT_ROTATION = 0.0f;
	public static final float DEFAULT_SCALE_X = 1.0f;
	public static final float DEFAULT_SCALE_Y = 1.0f;
	public static final int DEFAULT_REPEAT_X = 1;
	public static final int DEFAULT_REPEAT_Y = 1;
	public static final float DEFAULT_ALPHA = 1.0f;
	public static final int DEFAULT_DAMAGING_POWER = 0;
	public static final float DEFAULT_WEIGHT = 1.0f;
	public static final int DEFAULT_DIRECTION = 1;
	public static final int DEFAULT_START_DIRECTION = 1;
	public static final int DEFAULT_MAX_HEALTH = 100;
	public static final float DEFAULT_JUMP_POWER = 3.5f;
	public static final boolean DEFAULT_FLOOR_ONLY = false;
	public static final float DEFAULT_START_X = 0.0f;
	public static final float DEFAULT_START_Y = 0.0f;
	public static final float DEFAULT_SPEED = 0.7f;
	public static final float DEFAULT_WEAPON_POS_X = 0.0f;
	public static final float DEFAULT_WEAPON_POS_Y = 0.0f;

	public static final IGameObject createGameObjectFromMapFile(final MapkitItem mapKitItem, final DataLine dataLine) {
		final int gameObjectId = dataLine.getInt(0);
		IGameObject result = createGameObjectFromScratch(mapKitItem, gameObjectId);

		setUpGameObject(result, dataLine);

		result.setXY(dataLine.getFloat(3), dataLine.getFloat(4));
		return result;
	}

	public static final IGameObject createGameObjectFromScratch(final MapkitItem mapkitItem, final int gameObjectId) {
		IGameObject result;

		final int category = mapkitItem.getCategory();

		switch (category) {
			case Category.AREAS:
				result = Area.createArea(mapkitItem, gameObjectId);
				break;
	
			case Category.SCENERY:
				result = new Scenery(mapkitItem, gameObjectId);
				break;
	
			case Category.ACTOR:
				result = new Bot(mapkitItem, gameObjectId);
				break;
				
			case Category.DYNAMIC:
				result = new Dynamic(mapkitItem, gameObjectId);
				break;
			case Category.ANIMATED_SCENERY:
				result = new AnimatedScenery(mapkitItem, gameObjectId);
				break;
	
			default:
				result = null;
		}
		
		setUpGameObject(result, mapkitItem.getDataLine());

		return result;
	}

	public static final IGameObject setUpGameObject(final IGameObject o, final DataLine d) {

		try {
			if (o instanceof IRotatable) {
				final IRotatable r = (IRotatable) o;
				if (d.containsKey(DataKey.ROTATION))
					r.setRotation(d.getFloat(DataKey.ROTATION));
				else
					r.setRotation(DEFAULT_ROTATION);

			}

			if (o instanceof IScalable) {
				final IScalable r = (IScalable) o;
				if (d.containsKey(DataKey.SCALE_X) && d.containsKey(DataKey.SCALE_Y))
					r.setScale(d.getFloat(DataKey.SCALE_X), d.getFloat(DataKey.SCALE_Y));
				else
					r.setScale(DEFAULT_SCALE_X, DEFAULT_SCALE_Y);
			}

			if (o instanceof IRepeatable) {
				final IRepeatable r = (IRepeatable) o;
				if (d.containsKey(DataKey.REPEAT_X) && d.containsKey(DataKey.REPEAT_Y))
					r.setRepeat(d.getInt(DataKey.REPEAT_X), d.getInt(DataKey.REPEAT_Y));
				else
					r.setRepeat(DEFAULT_REPEAT_X, DEFAULT_REPEAT_Y);
			}

			if (o instanceof IAlphable) {
				final IAlphable r = (IAlphable) o;
				if (d.containsKey(DataKey.ALPHA))
					r.setAlpha(d.getFloat(DataKey.ALPHA));
				else
					r.setAlpha(DEFAULT_ALPHA);
			}

			if (o instanceof IDamaging) {
				final IDamaging r = (IDamaging) o;
				if (d.containsKey(DataKey.DAMAGING_POWER))
					r.setDamagingPower(d.getInt(DataKey.DAMAGING_POWER));
				else
					r.setDamagingPower(DEFAULT_DAMAGING_POWER);
			}

			if (o instanceof IGravitied) {

				final IGravitied r = (IGravitied) o;
				if (d.containsKey(DataKey.WEIGHT))
					r.setWeight(d.getFloat(DataKey.WEIGHT));
				else {
					r.setWeight(DEFAULT_WEIGHT);
				}

			}

			if (o instanceof IDirectioned) {
				final IDirectioned r = (IDirectioned) o;
				if (d.containsKey(DataKey.DIRECTION))
					r.setDirection(d.getInt(DataKey.DIRECTION));
				else
					r.setDirection(DEFAULT_DIRECTION);
				
				if (d.containsKey(DataKey.START_DIRECTION))
					r.setStartDirection(d.getInt(DataKey.START_DIRECTION));
				else
					r.setStartDirection(DEFAULT_START_DIRECTION);
			}

			if (o instanceof IMoveable) {
				final IMoveable r = (IMoveable) o;
				if (d.containsKey(DataKey.START_X) && d.containsKey(DataKey.START_Y))
					r.setStartXY(d.getFloat(DataKey.START_X), d.getFloat(DataKey.START_Y));
				else
					r.setStartXY(DEFAULT_START_X, DEFAULT_START_Y);

				if (d.containsKey(DataKey.SPEED))
					r.setSpeed(d.getFloat(DataKey.SPEED));
				else
					r.setSpeed(DEFAULT_SPEED);
			}

			if (o instanceof IDestroyable) {

				final IDestroyable r = (IDestroyable) o;
				if (d.containsKey(DataKey.MAX_HEALTH)) {
					r.setMaxHealth(d.getInt(DataKey.MAX_HEALTH));
				} else
					r.setMaxHealth(DEFAULT_MAX_HEALTH);

				r.setHealth(r.getMaxHealth());
			}

			if (o instanceof ICollisioned) {
				final ICollisioned r = (ICollisioned) o;

				final String collisionArea = d.getString(DataKey.COLLISION_AREA);
				if (collisionArea != null && !collisionArea.isEmpty()) {
					final String[] s = collisionArea.split(",");

					final float x = Float.parseFloat(s[0]);
					final float y = Float.parseFloat(s[1]);
					final float w = Float.parseFloat(s[2]);
					final float h = Float.parseFloat(s[3]);

					r.setCollisionArea(x, y, w, h);
				}
				if(d.containsKey(DataKey.COLLISION_ENABLED)) {
					r.setCollisionEnabled(d.getBoolean(DataKey.COLLISION_ENABLED));
				}
			}

			if (o instanceof ITight) {
				final ITight r = (ITight) o;
				if (d.containsKey(DataKey.FLOOR_ONLY))
					r.setFloorOnly(d.getBoolean(DataKey.FLOOR_ONLY));
				else
					r.setFloorOnly(DEFAULT_FLOOR_ONLY);
				
				if (d.containsKey(DataKey.PUSHABLE))
					r.setPushable(d.getBoolean(DataKey.PUSHABLE));
			}

			if (o instanceof Actor) {
				final Actor r = (Actor) o;
				if (d.containsKey(DataKey.JUMP_POWER))
					r.setJumpPower(d.getFloat(DataKey.JUMP_POWER));
				else
					r.setJumpPower(DEFAULT_JUMP_POWER);

				final float wx = d.containsKey(DataKey.WEAPON_POS_X) ? d.getFloat(DataKey.WEAPON_POS_X)
						: DEFAULT_WEAPON_POS_X;

				final float wy = d.containsKey(DataKey.WEAPON_POS_Y) ? d.getFloat(DataKey.WEAPON_POS_Y)
						: DEFAULT_WEAPON_POS_Y;

				r.setWeaponXY(wx, wy);

			}

			if (o instanceof Area) {
				final Area r = (Area) o;
				if (d.containsKey(DataKey.WIDTH) && d.containsKey(DataKey.HEIGHT))
					r.setSize(d.getFloat(DataKey.WIDTH), d.getFloat(DataKey.HEIGHT));

				if (o instanceof AreaDoorTeleport) {
					final AreaDoorTeleport at = (AreaDoorTeleport) r;
					if(d.containsKey(DataKey.TARGET_ROOM)) {
						at.setTargetRoom(d.getInt(DataKey.TARGET_ROOM));
						at.setTargetLocation(d.getInt(DataKey.TARGET_X), d.getInt(DataKey.TARGET_Y));
					}
				}

				if (o instanceof AreaCheckpoint) {
					final AreaCheckpoint ac = (AreaCheckpoint) r;
					if(d.containsKey(DataKey.CHECKPOINT_TYPE))
						ac.setCheckPointType(d.getInt(DataKey.CHECKPOINT_TYPE));
				}
				
				if (o instanceof AreaTrigger) {
					final AreaTrigger at = (AreaTrigger) r;
					if(d.containsKey(DataKey.TRIGGER_OPTIONS))
						at.setTriggerOptions(d.getString(DataKey.TRIGGER_OPTIONS));
				}
				
				if (o instanceof AreaDoorTeleportCp) {
					final AreaDoorTeleportCp at = (AreaDoorTeleportCp)r;
					if(d.containsKey(DataKey.CHECKPOINT))
						at.setTargetCheckpointId(d.getInt(DataKey.CHECKPOINT));
				}
				
				if (o instanceof AreaWater) {
					final AreaWater aw = (AreaWater)r;
					if(d.containsKey(DataKey.DENSITY))
						aw.setDensity(d.getFloat(DataKey.DENSITY));
				}
				
				if (o instanceof AreaWind) {
					final AreaWind aw = (AreaWind)r;
					if(d.containsKey(DataKey.WIND_X))
						aw.setWindX(d.getFloat(DataKey.WIND_X));
					if(d.containsKey(DataKey.WIND_Y))
						aw.setWindY(d.getFloat(DataKey.WIND_Y));
				}
			}
			
			if(o instanceof Bot) {
				final Bot r = (Bot)o;
				if(d.containsKey(DataKey.REACTS_ON_MARKERS))
					r.setReactsOnTriggers(d.getBoolean(DataKey.REACTS_ON_MARKERS));
				if(d.containsKey(DataKey.FACE_TO_FACE))
					r.setFace2Face(d.getBoolean(DataKey.FACE_TO_FACE));
				if(d.containsKey(DataKey.ACTION_PROGRAM))
					r.setActionProgram(ActionProgram.parse(d.getString(DataKey.ACTION_PROGRAM)));
			}
			

		} catch (Exception ex) {
			System.err.println("Error when set up game object");
			ex.printStackTrace();
		}

		return o;
	}

	public static final String gameObjectToDataLineString(final IGameObject o, final int layer) {
		final StringBuilder s = new StringBuilder(String.format("%d | %d | %d | %s | %s ", o.getId(),
				o.getMapkitItem().getId(), layer, roundEx(o.getX()), roundEx(o.getY())));

		if (o instanceof IRotatable) {
			final IRotatable r = (IRotatable) o;
			s.append(kv(DataKey.ROTATION, r.getRotation()));
		}

		if (o instanceof IScalable) {
			final IScalable r = (IScalable) o;
			s.append(kv(DataKey.SCALE_X, r.getScaleX()));
			s.append(kv(DataKey.SCALE_Y, r.getScaleY()));
		}

		if (o instanceof IRepeatable) {
			final IRepeatable r = (IRepeatable) o;
			s.append(kv(DataKey.REPEAT_X, r.getRepeatX()));
			s.append(kv(DataKey.REPEAT_Y, r.getRepeatY()));
		}

		if (o instanceof IAlphable) {
			final IAlphable r = (IAlphable) o;
			s.append(kv(DataKey.ALPHA, r.getAlpha()));
		}

		if (o instanceof IDamaging) {
			final IDamaging r = (IDamaging) o;
			s.append(kv(DataKey.DAMAGING_POWER, r.getDamagingPower()));
		}

		if (o instanceof IGravitied) {
			final IGravitied r = (IGravitied) o;
			s.append(kv(DataKey.WEIGHT, r.getWeight()));
		}

		if (o instanceof IDirectioned) {
			final IDirectioned r = (IDirectioned) o;
			s.append(kv(DataKey.DIRECTION, r.getDirection()));
			s.append(kv(DataKey.START_DIRECTION, r.getStartDirection()));
		}

		if (o instanceof IMoveable) {
			final IMoveable r = (IMoveable) o;
			s.append(kv(DataKey.START_X, r.getStartX()));
			s.append(kv(DataKey.START_Y, r.getStartY()));
			s.append(kv(DataKey.SPEED, r.getSpeed()));
		}

		if (o instanceof IDestroyable) {
			final IDestroyable r = (IDestroyable) o;
			s.append(kv(DataKey.MAX_HEALTH, r.getMaxHealth()));
		}

		if (o instanceof ICollisioned) {
			final ICollisioned r = (ICollisioned) o;

			final String collisionArea = String.format("%s,%s,%s,%s", roundEx(r.getCollisionX()),
					roundEx(r.getCollisionY()), roundEx(r.getCollisionWidth()), roundEx(r.getCollisionHeight()));

			s.append(kv(DataKey.COLLISION_AREA, collisionArea));
			s.append(kv(DataKey.COLLISION_ENABLED, r.isCollisionEnabled()));
		}

		if (o instanceof ITight) {
			final ITight r = (ITight) o;
			s.append(kv(DataKey.FLOOR_ONLY, r.isFloorOnly()));
			s.append(kv(DataKey.PUSHABLE, r.isPushable()));
		}

		if (o instanceof Actor) {
			final Actor r = (Actor) o;
			s.append(kv(DataKey.JUMP_POWER, r.getJumpPower()));
			s.append(kv(DataKey.WEAPON_POS_X, r.getWeaponX()));
			s.append(kv(DataKey.WEAPON_POS_Y, r.getWeaponY()));
		}

		if (o instanceof Area) {
			final Area r = (Area) o;
			s.append(kv(DataKey.AREA_TYPE, r.getAreaType()));
			s.append(kv(DataKey.WIDTH, r.getWidth()));
			s.append(kv(DataKey.HEIGHT, r.getHeight()));
			
			if (o instanceof AreaDoorTeleport) {
				final AreaDoorTeleport at = (AreaDoorTeleport) r;
				s.append(kv(DataKey.TARGET_ROOM, at.getTargetRoomId()));
				s.append(kv(DataKey.TARGET_X, at.getTargetX()));
				s.append(kv(DataKey.TARGET_Y, at.getTargetY()));
			}

			if (o instanceof AreaCheckpoint) {
				final AreaCheckpoint ac = (AreaCheckpoint) r;
				s.append(kv(DataKey.CHECKPOINT_TYPE, ac.getCheckPointType()));
			}
			
			if (o instanceof AreaTrigger) {
				final AreaTrigger at = (AreaTrigger) r;
				s.append(kv(DataKey.TRIGGER_OPTIONS, at.getTriggerOptions()));
			}
			
			if (o instanceof AreaDoorTeleportCp) {
				final AreaDoorTeleportCp at = (AreaDoorTeleportCp) r;
				s.append(kv(DataKey.CHECKPOINT, at.getTargetCheckpointId()));
			}
			
			if (o instanceof AreaWater) {
				final AreaWater aw = (AreaWater) r;
				s.append(kv(DataKey.DENSITY, aw.getDensity()));
			}

			if (o instanceof AreaWind) {
				final AreaWind aw = (AreaWind) r;
				s.append(kv(DataKey.WIND_X, aw.getWindX()));
				s.append(kv(DataKey.WIND_Y, aw.getWindY()));
			}

		}
		
		if(o instanceof Bot) {
			final Bot r = (Bot)o;
			final boolean reactsOnMarkers = r.isReactsOnTriggers();
			final boolean faceToFace = r.isFace2Face();
			s.append(kv(DataKey.REACTS_ON_MARKERS, reactsOnMarkers));
			s.append(kv(DataKey.FACE_TO_FACE, faceToFace));
			s.append(kv(DataKey.ACTION_PROGRAM, r.getActionProgram().stringify()));
		}
		
		return s.toString();
	}

	private static final String kv(final String k, Object v) {
		String type = null;

		if (v instanceof Float) {
			v = roundEx((float) v);
		}

		if (v instanceof Integer)
			type = "%d";
		else if (v instanceof Float)
			type = "%f";
		else if (v instanceof String)
			type = "%s";
		else if (v instanceof Boolean)
			type = "%b";

		final String format = "| %s = " + type + " ";
		return String.format(format, k, v);
	}

	private static final String roundEx(final float f) {
		final float result = (float) (Math.rint(100.0 * f) / 100);
		final boolean intgr = result % 1 == 0;
		if(intgr) return String.valueOf((int)result);
		return String.valueOf(result);
	}
}




















