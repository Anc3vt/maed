package ru.ancevt.maed.common;

public class PlayerController extends Controller {
	
	private static PlayerController instance;
	
	public static final PlayerController getInstance() {
		return instance == null ? instance = new PlayerController() : instance;
	}
	
	private PlayerController() {
		
	}
}
