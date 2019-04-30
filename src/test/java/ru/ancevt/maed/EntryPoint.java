package ru.ancevt.maed;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import ru.ancevt.d2d2.display.Stage;
import ru.ancevt.d2d2.display.text.BitmapFont;
import ru.ancevt.d2d2.pc.D2D2Window;
import ru.ancevt.maed.common.PlayerController;

public class EntryPoint {
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 800;
	
	public static void main(String[] args) {
		final D2D2Window window = new D2D2Window(SCREEN_WIDTH, SCREEN_HEIGHT) {

			private static final long serialVersionUID = 3296315089809377966L;

			@Override
			public void init() {
				try {
					BitmapFont.setDefaultBitmapFont(BitmapFont.loadBitmapFont("Terminus.bmf"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					d2d2Init(this);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};
		window.setLocationByPlatform(true);
		window.setTitle("D2D2 project (floating)");
		window.setVisible(true);
		
		/*
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		window.getContentPane().setCursor(blankCursor);
		*/
	}
	
	

	public static final void d2d2Init(final D2D2Window window) throws IOException {
		final Stage stage = window.getStage();
		
		stage.setFrameRate(60);
		stage.setScaleMode(Stage.SCALE_MODE_REAL);
		stage.setAlign(Stage.ALIGN_TOP_LEFT);
		stage.setStageSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		prepareInput(window.canvas());
	}
	
	private static final void prepareInput(Component component) {
		final PlayerController c = PlayerController.getInstance();
		
		component.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_A: c.setLeft(true); break;
					case KeyEvent.VK_W: c.setUp(true); break;
					case KeyEvent.VK_D: c.setRight(true); break;
					case KeyEvent.VK_S: c.setDown(true); break;
					case KeyEvent.VK_B: c.setC(true); break;
					case KeyEvent.VK_N: c.setB(true); break;
					case KeyEvent.VK_M: c.setA(true); break;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_A: c.setLeft(false); break;
					case KeyEvent.VK_W: c.setUp(false); break;
					case KeyEvent.VK_D: c.setRight(false); break;
					case KeyEvent.VK_S: c.setDown(false); break;
					case KeyEvent.VK_B: c.setC(false); break;
					case KeyEvent.VK_N: c.setB(false); break;
					case KeyEvent.VK_M: c.setA(false); break;
				}
			}
		});
    }
}

















