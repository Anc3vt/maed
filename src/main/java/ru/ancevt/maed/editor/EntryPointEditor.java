package ru.ancevt.maed.editor;

import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import ru.ancevt.d2d2.display.Stage;
import ru.ancevt.d2d2.display.text.BitmapFont;
import ru.ancevt.d2d2.pc.D2D2Window;
import ru.ancevt.maed.common.PlayerController;
import ru.ancevt.util.commandinterpreter.CommandInterpreter;

public class EntryPointEditor {
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 800;
	
	private static EditorRoot editorRoot;
	
	public static void main(String[] args) {
		final D2D2Window window = new D2D2Window(1500, 850) {

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
		window.setLocation(0, 32);
		window.setTitle("D2D2 project (floating)");
		window.setVisible(true);
		
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		java.awt.Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		window.getContentPane().setCursor(blankCursor);
	}

	public static final void d2d2Init(final D2D2Window window) throws IOException {
		final Stage stage = window.getStage();
		
		stage.setFrameRate(60);
		stage.setScaleMode(Stage.SCALE_MODE_REAL);
		stage.setAlign(Stage.ALIGN_TOP_LEFT);
		stage.setStageSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		prepareInput(window.canvas());
		
		editorRoot = new EditorRoot();
		
		
		// Entry point
		stage.setRoot(editorRoot);
		
		CommandInterpreter commandInterpreter = new CommandInterpreter();
		commandInterpreter.addCommand("load", (args)->{
			editorRoot.loadMap(args.getString(0));
		});
		commandInterpreter.startThread();
		commandInterpreter.execute("load map1.map");
	}
	
	private static final void prepareInput(Component component) {
		final PlayerController c = PlayerController.getInstance();
		
		component.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				editorRoot.mouseMoved(e.getX(), e.getY(), false);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				editorRoot.mouseMoved(e.getX(), e.getY(), true);
			}
		});
		
		component.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				editorRoot.update();
			}
		});
		
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
				
				final EditorController ec = EditorController.getInstance();
				ec.setShift(e.isShiftDown());
				ec.setControl(e.isControlDown());
				ec.setAlt(e.isAltDown());
				ec.putDown(e.getKeyCode());
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
				
				final EditorController ec = EditorController.getInstance();
				ec.setShift(e.isShiftDown());
				ec.setControl(e.isControlDown());
				ec.setAlt(e.isAltDown());
				ec.putUp(e.getKeyCode());
			}
		});
    }
}

















