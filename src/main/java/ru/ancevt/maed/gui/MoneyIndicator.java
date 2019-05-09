package ru.ancevt.maed.gui;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.d2d2.display.text.BitmapText;

public class MoneyIndicator extends DisplayObjectContainer {
	
	private Sprite icon;
	private BitmapText text;
	private int money;
	
	public MoneyIndicator() {
		icon = new Sprite("money_indicator");
		text = new BitmapText(Font.getGameFont());
		
		add(icon);
		add(text);
		text.setX(icon.getX() + icon.getWidth());
		
		text.setText("0");
	}

	public void setMoney(int money) {
		this.money = money;
		text.setText(String.valueOf(money));
	}
	
	public int getMoney() {
		return money;
	}

}
