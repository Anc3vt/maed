package ru.ancevt.maed.editor;

import java.util.ArrayList;
import java.util.List;

import ru.ancevt.d2d2.common.BorderedRect;
import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.display.texture.Texture;
import ru.ancevt.d2d2.panels.Button;
import ru.ancevt.d2d2.panels.DropList;
import ru.ancevt.d2d2.panels.DropListItem;
import ru.ancevt.d2d2.panels.Label;
import ru.ancevt.d2d2.panels.TitledPanel;
import ru.ancevt.d2d2.time.Timer;
import ru.ancevt.maed.map.Mapkit;
import ru.ancevt.maed.map.MapkitItem;

public class MapkitToolsPanel extends TitledPanel {
	
	private static final String TITLE = "Mapkit tools";
	private static final int ICON_SIZE = 38;
	
	private final DropList category;
	private final BorderedRect palette;
	private final Button pageNext;
	private final Button pagePrev;
	private final Label label;
	
	private Mapkit mapkit;
	private int currentPage;
	private int currentCategory;
	private List<Button> buttons;
	private boolean buttonsEnabled;
	
	public MapkitToolsPanel() {
		buttonsEnabled = true;
		
		buttons = new ArrayList<Button>();
		
		setTitleText("Mapkit tools");
		setSize(270, 420);
		
		palette = new BorderedRect(260, 345, Color.WHITE, Color.BLACK);
		palette.setXY(5, 38);
		add(palette);

		pagePrev = new Button("<-") {
			@Override
			public void onButtonPressed() {
				prevPage();
			}
		};
		pagePrev.setSize(30, 30);
		add(pagePrev, 5, 387);
		pageNext = new Button("->") {
			@Override
			public void onButtonPressed() {
				nextPage();
			}
		};
		pageNext.setSize(30, 30);
		add(pageNext, 40, 387);
		
		label = new Label("Label");
		label.setBounds(180, 30);
		add(label, 80, 387);
		
		category = new DropList() {
			
			@Override
			public void onClose() {
				final Timer timer = new Timer(500) {
					public void onTimerTick() {
						setAllButtonsEnabled(true);
					};
				};
				timer.start();
			}
			
			@Override
			public void onOpen() {
				setAllButtonsEnabled(false);
			}
			
			@Override
			public void onSelect(Object key) {
				currentCategory = (int)key;
				viewPage(currentPage);
			}
		};
		for(int i = 0; i <= MapkitItem.Category.ANIMATED_SCENERY; i ++) {
			final String name = MapkitItem.Category.getName(i);
			category.addItem(new DropListItem(name, i));
		}
		category.setWidth(260);
		add(category, 5, 10);
	}
	
	public void setMapkit(Mapkit mapkit) {
		this.mapkit = mapkit;
		viewPage(0);
	}
	
	public Mapkit getMapkit() {
		return mapkit;
	}
	
	private final void nextPage() {
		viewPage(++currentPage);
	}
	
	private final void prevPage() {
		if(currentPage == 0) return;
		viewPage(--currentPage);
	}
	
	private void setAllButtonsEnabled(boolean value) {
		this.buttonsEnabled = value;
		for (final Button b : buttons) {
			b.setEnabled(value);
		}
	}
	
	private void viewPage(int pageNumber) {
		if(mapkit == null) return;
		
		while(!buttons.isEmpty()) {
			buttons.remove(0).removeFromParent();
		}
		
		setTitleText(TITLE + " (" + currentPage + ")");
		
		int count = 0;
		int x = 5, y = 5;
		
	        for(int i = 0; i < mapkit.getItemCount(); i ++) {
			final MapkitItem mapkitItem = mapkit.getItem(i);
			
			if(currentCategory == 0 || mapkitItem.getCategory() == currentCategory) {
				count++;
				if(count > 48 * currentPage) {
					
					final Texture iconTexture = mapkitItem.getTexture();
					
					final Sprite icon = new Sprite(iconTexture);
					fixIconSize(icon);
					final Button button = new Button() {
						@Override
						public void onButtonPressed() {
							onMapkitItemSelected(mapkitItem);
							super.onButtonPressed();
						}
					};
					button.setBackgroundColor(Color.WHITE);
					button.setIcon(icon);
					button.setSize(40, 40);
					button.setXY(x, y);
					palette.add(button);
					button.setEnabled(buttonsEnabled);
		
					buttons.add(button);
					
					x += button.getWidth() + 2;
					if(x >= palette.getWidth() - ICON_SIZE) {
						x = 5;
						y += button.getHeight() + 2;
						if (y >= 300) break;
					}
				}
			}
		}
	}
	
	private static final void fixIconSize(Sprite icon) {
		while(icon.getWidth() * icon.getScaleX() < ICON_SIZE || icon.getHeight() * icon.getScaleY() < ICON_SIZE) {
			icon.setScale(
				icon.getScaleX() * 1.1f,
				icon.getScaleY() * 1.1f
			);
		}
		
		while(icon.getWidth() * icon.getScaleX() > ICON_SIZE || icon.getHeight() * icon.getScaleY() > ICON_SIZE) {
			icon.setScale(
				icon.getScaleX() * 0.9f,
				icon.getScaleY() * 0.9f
			);
		}
	}
	
	public void setMapkitItem(MapkitItem mapkitItem) {
		label.setText(mapkitItem.getId() + ", " + mapkitItem.getName());
	}

	public void onMapkitItemSelected(MapkitItem mapkitItem) {
		setMapkitItem(mapkitItem);
	}
}






















