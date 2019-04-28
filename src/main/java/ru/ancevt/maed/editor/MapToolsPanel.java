package ru.ancevt.maed.editor;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.panels.Button;
import ru.ancevt.d2d2.panels.DropList;
import ru.ancevt.d2d2.panels.DropListItem;
import ru.ancevt.d2d2.panels.Label;
import ru.ancevt.d2d2.panels.TextInput;
import ru.ancevt.d2d2.panels.TitledPanel;
import ru.ancevt.maed.map.Map;
import ru.ancevt.maed.map.Room;

public class MapToolsPanel extends TitledPanel {

	private final TextInput textInputGrav;
	private final DropList dropListRoom;
	private final Button buttonZoomIn;
	private final Button buttonZoomOut;
	private final TextInput textInputBG;
	private final TextInput textInputRoomWidth;
	private final TextInput textInputRoomHeight;
	
	private Map map;
	private Room room;
	
	public MapToolsPanel() {
		setTitleText("Map tools");
		setSize(510, 30);
		
		textInputGrav = new TextInput() {
			@Override
			public void onTextEnter() {
				updateModel();
			}
		};
		dropListRoom = new DropList() {
			@Override
			public void onSelect(Object key) {
				onSelectRoom((int)key);
				setRoom(map.getRoomById((int)key));
			}
		};
		buttonZoomIn = new Button("+") {
			@Override
			public void onButtonPressed() {
				onZoomInButtonPressed();
			}
		};
		buttonZoomOut = new Button("-") {
			@Override
			public void onButtonPressed() {
				onZoomOutButtonPressed();
			}
		};
		textInputBG = new TextInput() {
			@Override
			public void onTextEnter() {
				updateModel();
			}
		};
		textInputRoomWidth = new TextInput() {
			@Override
			public void onTextEnter() {
				updateModel();
			}
		};
		textInputRoomHeight = new TextInput() {
			@Override
			public void onTextEnter() {
				updateModel();
			}
		};
	
		add(new Label("Grav.:"), 5, 5);
		textInputGrav.setWidth(45);
		add(textInputGrav, 45, 3);
		add(new Label("Room #"), 100, 5);
		dropListRoom.setWidth(38);
		add(dropListRoom, 140, 3);
		buttonZoomIn.setSize(23, 20);
		add(buttonZoomIn, 182, 3);
		buttonZoomOut.setSize(23, 20);
		add(buttonZoomOut, 208, 3);
		add(new Label("BG:"), 250, 5);
		textInputBG.setWidth(45);
		add(textInputBG, 270, 3);
		add(new Label("Room size:"), 330, 5);
		textInputRoomWidth.setWidth(44);
		add(textInputRoomWidth, 395, 3);
		add(new Label("x"), 445, 5);
		textInputRoomHeight.setWidth(44);
		add(textInputRoomHeight, 456, 3);
	}

	public void setGravity(float gravity) {
		textInputGrav.setText(gravity + "");
	}
	
	public float getGravity() {
		return Float.parseFloat(textInputGrav.getText());
	}
	
	public void setRoomId(int roomId) {
		dropListRoom.select(roomId);
	}
	
	public int getRoomId() {
		return (int)dropListRoom.getSelectedKey();
	}
	
	public void setBG(String bg) {
		textInputBG.setText(bg);
	}
	
	public String getBG() {
		return textInputBG.getText();
	}
	
	public void setRoomWidth(int roomWidth) {
		textInputRoomWidth.setText(roomWidth + "");
	}
	
	public int getRoomWidth() {
		textInputBG.setText(textInputBG.getText().trim());
		textInputRoomWidth.setText(textInputRoomWidth.getText().trim());
		textInputRoomHeight.setText(textInputRoomHeight.getText().trim());
		return Integer.parseInt(textInputRoomWidth.getText());
	}
	
	public void setRoomHeight(int roomWidth) {
		textInputRoomHeight.setText(roomWidth + "");
	}
	
	public int getRoomHeight() {
		return Integer.parseInt(textInputRoomHeight.getText());
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;

		setRoom(map.getRoomByIndex(0));
		updateView();
		
		for(int i = 0; i < map.getRoomsCount(); i ++) {
			final Room room = map.getRoomByIndex(i);
			dropListRoom.addItem(new DropListItem(room.getId() + "", room.getId()));
		}
		
	}
	
	public void setRoom(Room room) {
		this.room = room;
		updateView();
	}
	
	public Room getRoom() {
		return this.room;
	}
	
	public void updateView() {
		if(map != null) {
			setGravity(map.getGravity());
		}
		
		if(room != null) {
			dropListRoom.setText(room.getId() + "");
			
			//setRoomId(room.getId());
			setBG(room.getBackColor().toHexString());
			setRoomWidth(room.getWidth());
			setRoomHeight(room.getHeight());
		}
	}
	
	private void updateModel() {
		if(map != null) {
			map.setGravity(getGravity());
		}
		if(room != null) {
			room.setBackColor(new Color(getBG()));
			
			int w = getRoomWidth();
			int h = getRoomHeight();

			while(w % 16 != 0) w--;
			while(h % 16 != 0) h--;
			
			setRoomWidth(w);
			setRoomHeight(h);
			
			room.setSize(w, h);
		}
		
		onUpdateModel();
	}
	
	public void onUpdateModel() {
		
	}
	
	public void onZoomInButtonPressed() {
		
	}
	
	public void onZoomOutButtonPressed() {
		
	}
	
	public void onSelectRoom(int roomId) {
		
	}
}















