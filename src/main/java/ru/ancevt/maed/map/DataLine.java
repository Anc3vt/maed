package ru.ancevt.maed.map;

import ru.ancevt.maed.common.TilesetZone;

public class DataLine {

	private static final String TRUE = "true";
	private static final String DELIMITER = "\\|";
	private static final String SUBDELIMITER = ";";
	private static final String EQUALS = "=";
	
	public static final DataLine parse(String sourceLine) {
		final String[] splitted = sourceLine.split(DELIMITER);
		
		final KV[] data = new KV[splitted.length];
		
		for(int i = 0; i < splitted.length; i ++) {
			data[i] = new KV(splitted[i]);
		}
		
		return new DataLine(data);
	}
	
	private KV[] data;
	
	private DataLine(KV[] data) {
		this.data = data;
	}
	
	public final String stringify() {
		final StringBuilder sb = new StringBuilder();
		
		for(final KV kv : data) {
			final String key = kv.getKey();
			final String value = kv.getValue();
			
			if(key != null) {
				sb.append(key + " = ");
			}
			
			sb.append(value + " | ");
		}
		
		return sb.toString().substring(0, sb.toString().length() - 2);
	}
	
	public final boolean containsKey(String key) {
		for(final KV kv : data)
			if(kv.getKey() != null && kv.getKey().equals(key)) return true;
		
		return false;
	}
	
	public final TilesetZone[] getTilesetZones(String key) {
		final String string = getString(key);
		
		if(string == null) return null;
		
		final String[] splitted = string.split(SUBDELIMITER);
		
		final TilesetZone[] tilesetZones = new TilesetZone[splitted.length];
		for(int i = 0; i < splitted.length; i ++) {
			final String tilisetSoneStirng = splitted[i];
			tilesetZones[i] = new TilesetZone(tilisetSoneStirng);
		}
		
		return tilesetZones;
	}
	
	public final TilesetZone getTilesetZone(String key) {
		return getTilesetZones(key)[0];
	}
	
	public final int size() {
		return data.length;
	}
	
	public final String getString(final int index) {
		if (index >= data.length || index < 0) return null;
		
		return data[index].getValue();
	}
	
	public final int getInt(final int index) {
		final String string = getString(index);
		if(string == null || string.length() == 0) return 0;
		
		return Integer.parseInt(string);
	}
	
	public final float getFloat(final int index) {
		final String string = getString(index);
		if(string == null) return 0;
		return Float.parseFloat(string);
	}
	
	public final boolean getBoolean(final int index) {
		final String string = getString(index);
		if(string == null) return false;
		return Boolean.getBoolean(string);
	}
	
	public final String getString(final String key) {
		for(final KV kv : data) {
			if(key.equals(kv.getKey())) return kv.getValue();
		}
		
		return null;
	}
	
	public final int getInt(final String key) {
		final String string = getString(key);
		if(string == null) return 0;
		return Integer.parseInt(string);
	}
	
	public final float getFloat(final String key) {
		final String string = getString(key);
		if(string == null) return 0;
		return Float.parseFloat(string);
	}
	
	public final boolean getBoolean(final String key) {
		final String string = getString(key);
		if(string == null) return false;
		return TRUE.equals(string);
	}
	
	
	private static class KV {
		private String key;
		private String value;
		
		public KV(String sourceKv) {
			sourceKv = sourceKv.trim();
			
			if(sourceKv.contains(EQUALS)) {
				final String[] splitted = sourceKv.split(EQUALS);
				final String key = splitted[0].trim();
				final String value = splitted.length == 1 ? "" : splitted[1].trim();
				
				this.key = key;
				this.value = value;
			} else {
				this.value = sourceKv.trim();
			}
			
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}
}






























