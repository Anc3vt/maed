package ru.ancevt.maed.editor;

import ru.ancevt.d2d2.common.D2D2;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.Mapkit;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.map.Room;

public class EditorGUI extends DisplayObjectContainer {

	private MapToolsPanel mapToolsPanel;
	private MapkitToolsPanel mapkitToolsPanel;

	public EditorGUI() {
		mapToolsPanel = new MapToolsPanel() {
			@Override
			public void onSelectRoom(int roomId) {
				EditorGUI.this.onSelectRoom(roomId);
			}
			
			@Override
			public void onUpdateModel() {
				EditorGUI.this.onUpdateModel();
			}
			
			@Override
			public void onZoomInButtonPressed() {
				EditorGUI.this.onZoomInButtonPressed();
			}
			
			@Override
			public void onZoomOutButtonPressed() {
				EditorGUI.this.onZoomOutButtonPressed();
			}
		};
		add(mapToolsPanel, 50, 100);
		
		mapkitToolsPanel = new MapkitToolsPanel() {
			@Override
			public void onMapkitItemSelected(MapkitItem mapkitItem) {
				EditorGUI.this.onMapkitItemSelected(mapkitItem);
				super.onMapkitItemSelected(mapkitItem);
			};
		};
		add(mapkitToolsPanel, D2D2.getRenderer().getWidth() - mapkitToolsPanel.getWidth(), 100);
	}
	
	public final void setMapkit(Mapkit mapkit) {
		mapkitToolsPanel.setMapkit(mapkit);
	}
	
	public final void setRoom(Room room) {
		mapToolsPanel.setRoom(room);
	}
	
	public final Room getRoom() {
		return mapToolsPanel.getRoom();
	}
	
	public Mapkit getMapkit() {
		return mapkitToolsPanel.getMapkit();
	}
	
	public void setMap(Map map) {
		mapToolsPanel.setMap(map);
		setMapkit(map.getMapkit());
	}
	
	public Map getMap() {
		return mapToolsPanel.getMap();
	}
	
	public void updateView() {
		mapToolsPanel.updateView();
	}
	
	public void onMapkitItemSelected(MapkitItem mapkitItem) {
		
	}
	
	public void onZoomInButtonPressed() {
		
	}
	
	public void onZoomOutButtonPressed() {
		
	}
	
	public void onSelectRoom(int roomId) {
		
	}
	
	public void onUpdateModel() {
		
	}
}
