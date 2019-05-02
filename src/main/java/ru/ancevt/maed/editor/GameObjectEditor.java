package ru.ancevt.maed.editor;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import ru.ancevt.d2d2.display.Color;
import ru.ancevt.d2d2.display.DisplayObject;
import ru.ancevt.d2d2.display.IDisplayObject;
import ru.ancevt.d2d2.display.Root;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.common.DataKey;
import ru.ancevt.maed.editor.awt.GameObjectEditWindow;
import ru.ancevt.maed.gameobject.GameObjectFactory;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IDirectioned;
import ru.ancevt.maed.gameobject.IGameObject;
import ru.ancevt.maed.gameobject.IMoveable;
import ru.ancevt.maed.gameobject.IRepeatable;
import ru.ancevt.maed.gameobject.IResettable;
import ru.ancevt.maed.gameobject.IRotatable;
import ru.ancevt.maed.gameobject.IScalable;
import ru.ancevt.maed.gameobject.area.Area;
import ru.ancevt.maed.gameobject.area.AreaCollision;
import ru.ancevt.maed.map.DataLine;
import ru.ancevt.maed.map.MapkitItem;
import ru.ancevt.maed.world.World;
import ru.ancevt.maed.world.WorldLayer;

public class GameObjectEditor {
	
	private static final int GRID_SIZE = 16;
	
	private boolean enabled;
	private final World world;
	private final Cursor cursor;
	
	private int mouseX, mouseY, worldMouseX, worldMouseY;
	private int oldMouseX, oldMouseY;
	private Sprite ghost;
	private MapkitItem mapkitItem, lastMapkiItem;
	private Root root;
	private int layerNum;
	private boolean snapToGrid;
	private boolean space;
	private List<IGameObject> selected;
	private SelectRectangle selectRectangle;
	private SelectArea selectArea;
	private List<Selection> selections;
	private IGameObject selectedGameObject;
	private IRepeatable repeating;
	private Area resizingArea;
	private EditorInfoText editorInfoText;
	private boolean moving;
	private float scale;
	private List<ShadowLabel> layerLabels;
	private List<CopyBufferItem> copyBuffer;
	private EditorController editorController;
	
	public GameObjectEditor(World world, EditorController editorController, EditorInfoText editorInfoText) {
		this.world = world;
		this.editorController = editorController;
		this.editorInfoText = editorInfoText;
		
		selected = new ArrayList<IGameObject>();
		layerLabels = new ArrayList<ShadowLabel>();
		copyBuffer = new ArrayList<CopyBufferItem>();
		
		root = this.world.getRoot();
		
		cursor = Cursor.getInstance();
		root.add(cursor);
		
		snapToGrid = false;
		
		selectRectangle = new SelectRectangle();
		selectArea = new SelectArea();
		
		selections = new ArrayList<Selection>();
		setEnabled(true);
	}
	
	public void mouseMove(int x, int y, int worldX, int worldY, boolean drag) {
		this.mouseX = x;
		this.mouseY = y;
		this.worldMouseX = worldX;
		this.worldMouseY = worldY;
		
		if(!isEnabled()) return;
		
		if(drag && space) {
			world.getCamera().move(
				(oldMouseX - x)/scale,
				(oldMouseY - y)/scale 
			);
		} else 
		if(drag) {
			if(!moving) {
				selectRectangle.setX2(worldX);
				selectRectangle.setY2(worldY);
				selectArea.setXY(selectRectangle);
			}
			
			if(repeating != null && selected.size() == 1 && selected.contains((IGameObject)repeating)) {
				final IGameObject gameObject = (IGameObject)repeating;
				
				final int repeatX = (int)((worldX - gameObject.getX()) / repeating.getOriginalWidth());
				final int repeatY = (int)((worldY - gameObject.getY()) / repeating.getOriginalHeight());
				
				if(repeatX > 0) repeating.setRepeatX(repeatX);
				if(repeatY > 0) repeating.setRepeatY(repeatY);
			} else
			if(resizingArea != null && selected.size() == 1 && selected.contains(resizingArea)) {
				final float w = worldX - resizingArea.getX();
				final float h = worldY - resizingArea.getY();
				
				if(w > 0 && h > 0) {
					resizingArea.setSize(worldX - resizingArea.getX(), worldY - resizingArea.getY());
				}
			} else 
				
			if(!selected.isEmpty() && moving) {
				moveSelected(
					(x - oldMouseX) / scale, 
					(y - oldMouseY) / scale
				);
			} else

			if(selectArea.getParent() != null && !space) {
				selectGameObjectsInSelectedArea();
			}
		} 

		oldMouseX = x;
		oldMouseY = y;
	}
	
	public void mouseDown(int x, int y, int worldX, int worldY) {
		if(!isEnabled()) return;
		
		if(mapkitItem != null && !space) {
			putObject(mapkitItem);
			mapkitItem = null;
		} else {
			selectRectangle.setUp(worldX, worldY, worldX, worldY);
			selectArea.setXY(selectRectangle);
			world.add(selectArea);
		}

		if(!space) {
			selectedGameObject = getGameObjectInLocation(worldX, worldY);

			if(!selected.contains(selectedGameObject) && !editorController.isShift()) unselect();
			
			if (selectedGameObject != null) {
				select(selectedGameObject);
				moving = true;
				
				if (worldX > selectedGameObject.getX() + selectedGameObject.getWidth()  - 8 &&
					worldY > selectedGameObject.getY() + selectedGameObject.getHeight() - 8 &&
					worldX < selectedGameObject.getX() + selectedGameObject.getWidth() &&
					worldY < selectedGameObject.getY() + selectedGameObject.getHeight()) {
					
					if(selectedGameObject instanceof IRepeatable)
						repeating = (IRepeatable)selectedGameObject; 
					else
					if(selectedGameObject instanceof Area)
						resizingArea = (Area)selectedGameObject;
				}
			} else unselect();
		}
		
		oldMouseX = x;
		oldMouseY = y;
	}
	
	public void mouseUp(int x, int y, int worldX, int worldY) {
		if(!isEnabled()) return;
		
		if(selectArea.hasParent())
			selectArea.removeFromParent();

		if(moving && snapToGrid) {
			snapToGridSelected();
		}
		
		repeating = null;
		resizingArea = null;
		moving = false;
	}
	
	public void keyDown(int keyCode) {
		if(!isEnabled()) return;
		
		int num = (keyCode - KeyEvent.VK_0 >= 0 && keyCode - KeyEvent.VK_0 <= 9) ? keyCode - KeyEvent.VK_0 : -1;
		
		if (num != -1) {
			
			layerNum = num;
			

			setLayer(num);
			
			if (EditorController.getInstance().isAlt()) {
				moveSelectedToLayer(num);
			}
			
		}

		switch (keyCode) {
		
			case KeyEvent.VK_R:
				if(selectedGameObject instanceof Area) {
					((Area) selectedGameObject).setSize(16, 16);
				}
				break;
		
			case KeyEvent.VK_ESCAPE:
				cancelPlacing();
				break;
			case KeyEvent.VK_ALT:
				snapToGrid = true;
				showLayer(layerNum);
				break;
			case KeyEvent.VK_SPACE:
				space = true;
				cursor.setVisible(false);
				break;
			case KeyEvent.VK_L:
				showLayerLabels();
				setDynamicCollisionsVisible(true);
				break;
			case KeyEvent.VK_DELETE:
				deleteSelected();
				break;
			case KeyEvent.VK_C:
				if(EditorController.getInstance().isControl()) copy();
				break;
			case KeyEvent.VK_X:
				if(EditorController.getInstance().isControl()) cut();	
				break;
			case KeyEvent.VK_V:
				if(EditorController.getInstance().isControl()) 
					paste();
				else
					setAreasVisible(!isAreasVisible());
				break;
			case KeyEvent.VK_PAGE_UP:
				rotateRightSelected();
				break;
			case KeyEvent.VK_PAGE_DOWN:
				rotateLeftSelected();
				break;	
			case KeyEvent.VK_BACK_SPACE:
				if(selectedGameObject instanceof AreaCollision ) {
					final AreaCollision t = (AreaCollision)selectedGameObject;
					t.setFloorOnly(!t.isFloorOnly());
				} else 
					reverseDirection();
				break;
			case KeyEvent.VK_K:
				if(lastMapkiItem != null) startPlacing(lastMapkiItem);
				break;
				
			case KeyEvent.VK_LEFT:
				moveSelected(-1, 0);
				break;
				
			case KeyEvent.VK_RIGHT:
				moveSelected(1, 0);
				break;
				
			case KeyEvent.VK_DOWN:
				moveSelected(0, 1);
				break;
				
			case KeyEvent.VK_UP:
				moveSelected(0, -1);
				break;
				
			case KeyEvent.VK_ENTER:
				if(selectedGameObject != null) openGameObjectEditWindow(selectedGameObject);
				break;
				
		}
	}
	
	public void keyUp(int keyCode) {
		if(!isEnabled()) return;
		
		switch (keyCode) {
			case KeyEvent.VK_ALT:	
				snapToGrid = false;
				showAllLayers();
				break;
			case KeyEvent.VK_SPACE:	
				space = false;
				cursor.setVisible(true);
				break;
			case KeyEvent.VK_L:	
				removeLayerLabels();
				setDynamicCollisionsVisible(false);
				break;
		}
	}
	
	private final void showAllLayers() {
		for(int i = 0; i < WorldLayer.LAYER_COUNT; i ++) {
			final WorldLayer layer = world.getLayer(i);
			layer.setAlpha(1.0f);
		}
	}
	
	private final void showLayer(int layerNum) {
		showAllLayers();
		
		for(int i = 0; i < WorldLayer.LAYER_COUNT; i ++) {
			if(i == layerNum) continue;
			final WorldLayer layer = world.getLayer(i);
			layer.setAlpha(0.1f);
		}
	}
	
	private final void openGameObjectEditWindow(IGameObject gameObject) {
		final String dataLineString = GameObjectFactory.gameObjectToDataLineString(gameObject, getLayerNumberOf(gameObject));
		
		SwingUtilities.invokeLater(()->{
			
			final GameObjectEditWindow w = new GameObjectEditWindow(dataLineString) {
				private static final long serialVersionUID = 1L;
	
				@Override
				public void onOKButtonPressed() {
					final String dataLineString = getDataLineString();
					GameObjectFactory.setUpGameObject(gameObject, DataLine.parse(dataLineString));
				}
			};
			w.setTitle(gameObject.toString() + " (floating)");
			w.setLocationByPlatform(true);
			w.setVisible(true);
			
		});
	}
	
	public void applyPlaySettings() {
		setAreasVisible(false);
		
	}
	
	public final void setAreasVisible(boolean value) {
		world.setAreasVisible(value);
	}
	
	public final boolean isAreasVisible() {
		return world.isAreasVisible();
	}
	
	public final void cut() {
		copy();
		deleteSelected();
	}
	
	public final void copy() {
		copyBuffer.clear();
		
		for(int i = 0; i < selected.size(); i ++) {
			final IGameObject gameObject = selected.get(i);
			copyBuffer.add(new CopyBufferItem(gameObject, getLayerNumberOf(gameObject)));
		}
	}
	
	public final void paste() {
		unselect();
		
		for(int i = 0; i < copyBuffer.size(); i ++) {
			final CopyBufferItem copyBufferItem = copyBuffer.get(i);
			final IGameObject gameObject = copyBufferItem.gameObject;
			final WorldLayer worldLayer = world.getLayer(copyBufferItem.layer);
			final IGameObject copied = gameObject.copy();
			((DisplayObject)copied).move(8, 8);
			
			world.addGameObject(copied, worldLayer.getIndex(), true);
			select(copied);
		}
	}
	
	private final void setDynamicCollisionsVisible(boolean value) {
		for(int i = 0; i < world.getGameObjectsCount(); i ++) {
			final IGameObject gameObject = world.getGameObject(i);
			if(gameObject instanceof ICollisioned) {
				final ICollisioned c = (ICollisioned)gameObject;
				c.setCollisionVisible(value);
			}
		}
	}
	
	private final void showLayerLabels() {
		if(layerLabels.isEmpty()) {
			for(int i = 0; i < world.getGameObjectsCount(); i ++) {
				final IGameObject gameObject = world.getGameObject(i);
				final String labelText = new String() + getLayerNumberOf(gameObject);
				final ShadowLabel label = new ShadowLabel(labelText);
				label.setColor(Color.createRandomColor());
				label.setXY(gameObject.getX(), gameObject.getY());
				layerLabels.add(label);
				world.add(label);
			}
		}
	}
	
	private final void removeLayerLabels() {
		while(!layerLabels.isEmpty()) {
			final ShadowLabel l = layerLabels.remove(0);
			l.removeFromParent();
		}
	}
	
	public final int getLayerNumberOf(final IGameObject gameObject) {
		final DisplayObject d = (DisplayObject)gameObject;
		final WorldLayer layer = (WorldLayer)d.getParent();
		return layer != null ? layer.getIndex() : 0;
	}
	
	private final void rotateRightSelected() {
		for (int i = 0; i < selected.size(); i++) {
			final IGameObject gameObject = selected.get(i);
			if(gameObject instanceof IRotatable) {
				final IRotatable r = (IRotatable) gameObject;
				r.rotate(90);
			}
		}
	}
	
	private final void rotateLeftSelected() {
		for (int i = 0; i < selected.size(); i++) {
			final IGameObject gameObject = selected.get(i);
			if(gameObject instanceof IRotatable) {
				final IRotatable r = (IRotatable) gameObject;
				r.rotate(-90);
			}
		}
	}
	
	private final void reverseDirection() {
		for (int i = 0; i < selected.size(); i++) {
			final IGameObject gameObject = selected.get(i);
			if(gameObject instanceof IDirectioned) {
				final IDirectioned d = (IDirectioned) gameObject;
				d.setDirection(d.getDirection() * -1);
				d.setStartDirection(d.getDirection());
				
			}  
			if(gameObject instanceof IScalable) {
				final IScalable s = (IScalable) gameObject;
				s.setScaleX(s.getScaleX() > 0 ? -1 : 1);
			}
		}
	}

	private final void moveSelectedToLayer(int layerNum) {
		final List<IGameObject> gameObjects = new ArrayList<IGameObject>();
		
		for (int i = 0; i < selected.size(); i++) {
			final IGameObject gameObject = selected.get(i);
			world.removeGameObject(gameObject, true);
			world.addGameObject(gameObject, layerNum, true);
			gameObjects.add(gameObject);
		}
		
		unselect();
		
		while(!gameObjects.isEmpty())
			select(gameObjects.remove(0));
	}
	
	private void snapToGridSelected() {
		for(int i = 0; i < selected.size(); i ++) {
			final IGameObject gameObject = selected.get(i);
			snapToGrid(gameObject);
			
			if(gameObject instanceof Area) {
				final Area a = (Area)gameObject;
				while((int)a.getWidth() % GRID_SIZE != 0) a.setWidth(a.getWidth() + 1);
				while((int)a.getHeight() % GRID_SIZE != 0) a.setHeight(a.getHeight() + 1);
			}
		}
	}
	
	private void snapToGrid(IDisplayObject displayObject) {
		int x = (int)displayObject.getX();
		int y = (int)displayObject.getY();
		
		while(x % GRID_SIZE != 0) x--;
		while(y % GRID_SIZE != 0) y--;
		
		displayObject.setXY(x, y);
	}
	
	public void deleteSelected() {
		for(final IGameObject gameObject : selected) {
			world.removeGameObject(gameObject, true);
		}
		unselect();
	}
	
	public void moveSelected(float x, float y) {
		for(final IGameObject gameObject : selected) {
			final DisplayObject asDisplayObject = (DisplayObject)gameObject;
			asDisplayObject.move(x, y);
			
			if(gameObject instanceof IResettable && gameObject instanceof IMoveable) {
				final IMoveable m = (IMoveable) gameObject;
				m.setStartXY(gameObject.getX(), gameObject.getY());
			}
		}
	}
	
	public final void select(IGameObject gameObject) {
		if(!selected.contains(gameObject)) selected.add(gameObject);
		updateSelecting();
		
		editorInfoText.selectGameObject(gameObject);
	}
	
	public final void unselect(final IGameObject gameObject) {
		selected.remove(gameObject);
		final Selection selection = getSelectionByGameObject(gameObject);
		if(selection == null) return;
		if(selection.hasParent())
			selection.getParent().remove(selection);
		
		selections.remove(selection);
	}
	
	private final Selection getSelectionByGameObject(final IGameObject gameObject) {
		for(final Selection selection : selections)
			if(selection.getGameObject() == gameObject)
				return selection;
		
		return null;
	}
	
	public synchronized final void unselect() {
		while(!selected.isEmpty()) {
			final IGameObject gameObject = selected.get(0);
			unselect(gameObject);
		}
	}
	
	private final void clearSelections() {
		while(!selections.isEmpty()) {
			final Selection selection = selections.remove(0);
			if(selection.hasParent()) selection.removeFromParent();
		}
	}
	
	private final void updateSelecting() {
		clearSelections();
		
		for(int i = 0; i < selected.size(); i ++) {
			final IGameObject gameObject = selected.get(i);
			final Selection selection = new Selection(gameObject);
			selection.setXY(gameObject.getX(), gameObject.getY());
			
			selections.add(selection);
			getWorldLayerByGameObject(gameObject).add(selection);
		}
	}
	
	private final WorldLayer getWorldLayerByGameObject(IGameObject gameObject) {
		for(int i = 0; i < WorldLayer.LAYER_COUNT; i ++) {
			if(world.getLayer(i) == gameObject.getParent()) return world.getLayer(i);
		}
		
		return null;
	}
	
	private final IGameObject getGameObjectInLocation(final int x, final int y) {
		final int count = world.getGameObjectsCount();
		for(int i = count-1; i >= 0; i --) { 

			final IGameObject gameObject = world.getGameObject(i);
			final DisplayObject asDisplayObject = (DisplayObject)gameObject;
			final WorldLayer worldLayer = (WorldLayer)asDisplayObject.getParent();
			
			if((worldLayer != null && layerNum == worldLayer.getIndex()) || editorController.isControl()) {
				if (x > gameObject.getX() && 
					x < gameObject.getX() + asDisplayObject.getWidth() &&
					y > gameObject.getY() && 
					y < gameObject.getY() + asDisplayObject.getHeight()) {
					return gameObject;
				}
			}
		}
		
		return null;
	}
	
	private final void selectGameObjectsInSelectedArea() {
		final float sX = selectArea.getX();
		final float sY = selectArea.getY();
		final float sW = selectArea.getWidth();
		final float sH = selectArea.getHeight();
		
		final int count = world.getGameObjectsCount();
		for(int i = 0; i < count; i ++) { 
			final IGameObject gameObject = world.getGameObject(i);
			final DisplayObject asDisplayObject = (DisplayObject)gameObject;
			final WorldLayer worldLayer = (WorldLayer)asDisplayObject.getParent();
			
			if ((worldLayer != null && layerNum == worldLayer.getIndex()) || 
				editorController.isControl()) {
				
				final float cX = gameObject.getX();
				final float cY = gameObject.getY();
				final float cW = asDisplayObject.getWidth();
				final float cH = asDisplayObject.getHeight();
				
				if(
					sX + sW > cX &&
					sX < cX + cW &&
					sY + sH > cY &&
					sY < cY + cH	
				) {
					select(gameObject);
				} else {
					unselect(gameObject);
				}
			}
		}
	}
	
	private final void putObject(MapkitItem mapkitItem) {
		final IGameObject gameObject = GameObjectFactory.createGameObjectFromScratch(mapkitItem, world.getMap().getNextFreeGameObjectId());
		
			
		gameObject.setXY(worldMouseX, worldMouseY);
		
		if(gameObject instanceof IMoveable) {
			final IMoveable m = (IMoveable)gameObject;
			m.setStartXY(worldMouseX, worldMouseY);
		}
		
		world.addGameObject(gameObject, layerNum, true);
		stopPlacing();
		
		if(snapToGrid) snapToGrid(gameObject);
	}
	
	public void process() {
		if(ghost != null) {
			ghost.setXY(worldMouseX, worldMouseY);
			if(snapToGrid) snapToGrid(ghost);
		}
		
		scale = world.getAbsoluteScaleX();
		
		cursor.setXY(mouseX, mouseY);
	}
	
	private final void activateGhost() {
		if(mapkitItem == null) throw new RuntimeException("mapkitItem must be not null");
		ghost = new Sprite();
		ghost.setAlpha(0.75f);
		ghost.setTexture(mapkitItem.getTexture());
		ghost.setXY(worldMouseX, worldMouseY);
		world.add(ghost);
		cursor.setVisible(false);
	}
	
	private final void stopPlacing() {
		if(ghost != null) {
			if(ghost.hasParent())
				ghost.removeFromParent();
		
			ghost = null;
		}
		cursor.setVisible(true);
		cursor.setColor(Color.WHITE);
	}
	
	public void cancelPlacing() {
		stopPlacing();
		mapkitItem = null;
	}
	
	public void startPlacing(MapkitItem mapkitItem) {
		this.mapkitItem = mapkitItem;
		this.lastMapkiItem = mapkitItem;
		stopPlacing();
		
		if(mapkitItem.getCategory() == MapkitItem.Category.AREAS) {
			final int areaType = mapkitItem.getDataLine().getInt(DataKey.AREA_TYPE);
			switch (areaType) {
				case Area.COLLISION:
					cursor.setColor(Color.BLACK);
					break;
				case Area.CHECKPOINT:
					cursor.setColor(Color.GREEN);
					break;
				case Area.DAMAGING:
					cursor.setColor(Color.DARK_RED);
					break;
				case Area.DOOR_TELEPORT:
					cursor.setColor(Color.DARK_GREEN);
					break;
				case Area.HOOK:
					cursor.setColor(Color.LIGHT_GRAY);
					break;
				case Area.TRIGGER:
					cursor.setColor(Color.MAGENTA);
					break;
			}
		} else
		
		activateGhost();
	}

	public int getLayer() {
		return layerNum;
	}

	public void setLayer(int layer) {
		this.layerNum = layer;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

class CopyBufferItem {
	
	public IGameObject gameObject;
	public int layer;
	
	public CopyBufferItem(IGameObject gameObject, final int layer) {
		this.gameObject = gameObject;
		this.layer = layer;
	}
}
