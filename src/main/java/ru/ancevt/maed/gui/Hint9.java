package ru.ancevt.maed.gui;

import java.util.ArrayList;
import java.util.List;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.touch.TouchButton;
import ru.ancevt.maed.editor.ShadowLabel;

public class Hint9 extends DisplayObjectContainer {
	
	private static final int PADDING = 10;
	
	private List<Sprite> sprites;
	private int cellWidth, cellHeight;
	
	private ShadowLabel label;
	private TouchButton touchButton;
	
	public Hint9(int cellWidth, int cellHeight) {
		sprites = new ArrayList<Sprite>();
		
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		touchButton = new TouchButton(true) { 
			@Override
			public boolean onTouchDown(int x, int y) {
				onTouch();
				return super.onTouchDown(x, y);
			}
		};
		add(touchButton);
		
		
		label = new ShadowLabel();
		label.setColor(Color.YELLOW);
		redraw();
	}
	
	private final void clear() {
		for(final Sprite s : sprites) {
			s.removeFromParent();
		}
	}
	
	public void onTouch() {
		
	}
	
	private final void redraw() {
		clear();
		
		for(int y = 0; y < cellHeight; y ++) {
			for(int x = 0; x < cellWidth; x ++) {
				
				String textureKey = null;
				
				if(x == 0 && y == 0) textureKey = "hint_lt"; else
				if(x == 0 && y > 0 && y < cellHeight-1) textureKey = "hint_l"; else
				if(x == 0 && y > 0) textureKey = "hint_lb"; else
				if(y == 0 && x > 0 && x < cellWidth-1) textureKey = "hint_t"; else
				if(y == 0 && x > 0) textureKey = "hint_rt"; else
				if(y == cellHeight -1 && x > 0 && x < cellWidth-1) textureKey = "hint_b"; else
				if(y == cellHeight -1 && x > 0) textureKey = "hint_rb"; else
				if(x != 0 && x != cellWidth -1 && y != 0 && y != cellHeight -1) textureKey = "hint_c"; else
				textureKey = "hint_r";
				
				final Sprite sprite = new Sprite(textureKey);
				add(sprite, x * 16, y * 16);
				
				sprites.add(sprite);
			}
		}
		
		label.setXY(PADDING, PADDING);
		label.setBounds(getWidth() - PADDING * 2, getHeight() - PADDING * 2);
		touchButton.setSize(getWidth(), getHeight());
		add(label);
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	public String getText() {
		return label.getText();
	}
	
	public void setCellSize(int w, int h) {
		setCellWidth(w);
		setCellHeight(h);
	}

	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
		redraw();
	}
	
	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
		redraw();
	}
	
}























