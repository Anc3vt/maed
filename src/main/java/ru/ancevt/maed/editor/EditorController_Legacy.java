package ru.ancevt.maed.editor;

import ru.ancevt.maed.map.MapkitItem;

public class EditorController_Legacy {
	
	private static EditorController_Legacy instance;
	
	public static final EditorController_Legacy getInstance() {
		return instance == null ? instance = new EditorController_Legacy() : instance;
	}
	
	public static final int OFF = -1;
	
	private boolean toggleGridVisible;
	private int layerToSwitch;
	private MapkitItem mapKitItemToPlace;

	private boolean ctrl, shift, alt, space;
	private boolean escape, delete, copy, paste, cut, backSpace;
	private boolean left, right, up, down;
	private boolean lastMapKitItem;
	private boolean rotation;
	private boolean cameraBoundsLock;
	private boolean togglePackSceneries;
	private boolean toggleAreasVisible;
	private boolean togglePlayMode;
	
	private String mapKitFileToCreateMap;
	private String mapFileToOpen;
	
	private EditorController_Legacy() {
		resetLayerToSwitch();
	}
	
	public final void togglePlayMode() {
		togglePlayMode = true;
	}
	
	public final boolean resetTogglePlayMode() {
		final boolean result = togglePlayMode;
		togglePlayMode = false;
		return result;
	}
	
	public final void toggleAreasVisible() {
		this.toggleAreasVisible = true;
	}
	
	public final boolean resetToggleAreasVisible() {
		final boolean result = toggleAreasVisible;
		toggleAreasVisible = false;
		return result;
	}
	
	public final void togglePackSceneries() {
		togglePackSceneries = true;
	}
	
	public final boolean resetTogglePackSceneries() {
		final boolean result = togglePackSceneries;
		togglePackSceneries = false;
		return result;
	}
	
	public final void cameraBoundsLock() {
		cameraBoundsLock = true;
	}
	
	public final boolean resetCameraBoundsLock() {
		final boolean result = cameraBoundsLock;
		cameraBoundsLock = false;
		return result;
	}
	
	public final void setMapFileToOpen(final String fileName) {
		this.mapFileToOpen = fileName;
	}
	
	public final String resetMapFileToOpen() {
		final String result = mapFileToOpen;
		mapFileToOpen = null;
		return result;
	}
	
	public final void setMapKitFileToCreateMap(String mapKitFileToCreateMap) {
		this.mapKitFileToCreateMap = mapKitFileToCreateMap;
	}
	
	public final String resetMapKitFileToCreateMap() {
		final String result = mapKitFileToCreateMap;
		mapKitFileToCreateMap = null;
		return result;
	}
	
	public int resetLayerToSwitch() {
		final int result = layerToSwitch;
		layerToSwitch = OFF;
		return result;
	}

	public void setLayerToSwitch(int layerToSwitch) {
		this.layerToSwitch = layerToSwitch;
	}

	public MapkitItem resetMapKitItemToPlace() {
		final MapkitItem result = mapKitItemToPlace;
		mapKitItemToPlace = null;
		return result;
	}

	public void setMapKitItemToPlace(MapkitItem mapKitItemToPlace) {
		this.mapKitItemToPlace = mapKitItemToPlace;
	}

	public boolean isAlt() {
		return alt;
	}

	public void setAlt(boolean alt) {
		this.alt = alt;
	}

	public boolean isShift() {
		return shift;
	}

	public void setShift(boolean shift) {
		this.shift = shift;
	}

	public boolean isCtrl() {
		return ctrl;
	}

	public void setCtrl(boolean ctrl) {
		this.ctrl = ctrl;
	}

	public boolean isSpace() {
		return space;
	}

	public void setSpace(boolean space) {
		this.space = space;
	}
	
	public void toggleGridVisible() {
		toggleGridVisible = true;
	}

	public boolean resetGridVisible() {
		final boolean result = toggleGridVisible;
		toggleGridVisible = false;
		return result;
	}
	
	public final void escape() {
		escape = true;
	}
	
	public final boolean resetEscape() {
		final boolean result = escape;
		escape = false;
		return result;
	}

	public final void selectLastMapKitItem() {
		lastMapKitItem = true;
	}
	
	public final boolean resetLastMapKitItem() {
		final boolean result = lastMapKitItem;
		lastMapKitItem = false;
		return result;
	}
	
	public final void delete() {
		this.delete = true;
	}
	
	public final boolean resetDelete() {
		final boolean result = delete;
		delete = false;
		return result;
	}
	
	public final void copy() {
		this.copy = true;
	}
	
	public final boolean resetCopy() {
		final boolean result = copy;
		copy = false;
		return result;
	}
	
	public final void paste() {
		paste = true;
	}
	
	public final boolean resetPaste() {
		final boolean result = paste;
		paste = false;
		return result;
	}
	
	public final void cut() {
		cut = true;
	}
	
	public final boolean resetCut() {
		final boolean result = cut;
		cut = false;
		return result;
	}
	
	public final void left() {
		left = true;
	}
	
	public final boolean resetLeft() {
		final boolean result = left;
		left = false;
		return result;
	}
	
	public final void right() {
		right = true;
	}
	
	public final boolean resetRight() {
		final boolean result = right;
		right = false;
		return result;
	}
	
	public final void up() {
		up = true;
	}
	
	public final boolean resetUp() {
		final boolean result = up;
		up = false;
		return result;
	}
	
	public final void down() {
		down = true;
	}
	
	public final boolean resetDown() {
		final boolean result = down;
		down = false;
		return result;
	}

	public final boolean isRotation() {
		return rotation;
	}

	public final void setRotation(boolean rotation) {
		this.rotation = rotation;
	}
	
	public final void backSpace() {
		backSpace = true;
	}
	
	public final boolean resetBackSpace() {
		final boolean result = backSpace;
		backSpace = false;
		return result;
	}
}


























