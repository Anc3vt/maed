package ru.ancevt.maed.world;

import ru.ancevt.d2d2.display.DisplayObjectContainer;
import ru.ancevt.maed.common.Viewport;
import ru.ancevt.maed.gameobject.IDirectioned;
import ru.ancevt.maed.gameobject.IGameObject;

public class Camera implements IDirectioned {
	
	public static final int VIEWPORT_WIDTH = Viewport.WIDTH;
	public static final int VIEWPORT_HEIGHT = Viewport.HEIGHT;
	public static final float DEFAULT_ZOOM = 1.0f; 
	
	private static final float MIN_ZOOM = 0.2f;
	private static final float MAX_ZOOM = 3.0f;
	
	private final IWorld world;
	private boolean boundsLock;
	
	private int boundWidth, boundHeight;
	private IGameObject attachedTo;
	private int direction;
	private boolean autoZoom;
	
	public Camera(final IWorld world) {
		this.world = world;
	}
	
	public final float getZoom() {
		return cameraLayer().getScaleX();
	}
	
	public final void zoom(final float delta) {
		setZoom(getZoom() * delta);
	}

	public final void setZoom(float zoom) {
		if(zoom < 1.1f) zoom = 1.0f;
		
		cameraLayer().setScale(zoom, zoom);
		if(cameraLayer().getScaleX() < MIN_ZOOM)
			cameraLayer().setScale(MIN_ZOOM, MIN_ZOOM);
		else if(cameraLayer().getScaleX() > MAX_ZOOM)
			cameraLayer().setScale(MAX_ZOOM, MAX_ZOOM);
		
		if(isBoundsLock()) fixBounds();
	}

	public final IWorld getWorld() {
		return world;
	}
	
	public final float getX() {
		return -world.getX();
	}
	
	public final float getY() {
		return -world.getY();
	}
	
	public final void setXY(final float x, final float y) {
		world.setXY(-x, -y);
		//if(isBoundsLock()) fixBounds();
	}
	
	public final void setX(final float x) {
		world.setX(-x);
		//if(isBoundsLock()) fixBounds();
	}
	
	public final void setY(final float y) {
		world.setY(-y);
		//if(isBoundsLock()) fixBounds();
	}
	
	public final void move(final float toX, final float toY) {
		world.move(-toX / getZoom(), -toY / getZoom());
		if(isBoundsLock()) fixBounds();
	}
	
	public final void moveX(final float value) {
		world.moveX(-value / getZoom());
		if(isBoundsLock()) fixBounds();
	}
	
	public final void moveY(final float value) {
		world.moveY(-value / getZoom());
		if(isBoundsLock()) fixBounds();
	}
	
	public final DisplayObjectContainer cameraLayer() {
		return world.getParent();
	}

	public boolean isBoundsLock() {
		return boundsLock;
	}

	public void setBoundsLock(boolean boundsLock) {
		this.boundsLock = boundsLock;
		if(boundsLock) fixBounds();
	}
	
	private final void fixBounds() {	
		if (boundWidth == 0 || boundHeight == 0) return;
		
		
		final int halfViewportWidth = VIEWPORT_WIDTH / 2;
		final int halfViewportHeight = VIEWPORT_HEIGHT / 2;

		final float z = getZoom();
		
		final float minLimitX = (float)halfViewportWidth / z;
		final float minLimitY = (float)halfViewportHeight / z;
		final float maxLimitX = (boundWidth - halfViewportWidth / z);
		final float maxLimitY = (boundHeight - halfViewportHeight / z); 
		
		try {
			
			if(getX() < minLimitX) setX(minLimitX); else
			if(getX() > maxLimitX) setX(maxLimitX);
	
			if(getY() < minLimitY) setY(minLimitY); else
			if(getY() > maxLimitY) setY(maxLimitY);
			
		} catch(StackOverflowError e) {
			setZoom(getZoom() + 0.05f);
		}
	}

	public int getBoundHeight() {
		return boundHeight;
	}

	public void setBoundHeight(int boundHeight) {
		this.boundHeight = boundHeight;
	}

	public int getBoundWidth() {
		return boundWidth;
	}

	public void setBoundWidth(int boundWidth) {
		this.boundWidth = boundWidth;
	}
	
	public void setBounds(final int w, final int h) {
		setBoundWidth(w);
		setBoundHeight(h);
		if(isBoundsLock()) fixBounds();
	}

	public IGameObject getAttachedTo() {
		return attachedTo;
	}

	public void setAttachedTo(IGameObject attachedTo) {
		this.attachedTo = attachedTo;
	}
	
	public final void process() {
		processAttached();
		processAutoZoom();
	}
	
	private final void processAutoZoom() {
		if(!autoZoom) return;
		
		final float speed = 0.005f;
		
		final float zoom = getZoom();
		
		if(Math.abs(zoom - 1.0f) < 0.005f) {
			setZoom(DEFAULT_ZOOM);
			return;
		}
		
		if(zoom > 1.0f) setZoom(zoom - speed); else
		if(zoom < 1.0f) setZoom(zoom + speed);
	}
	
	private final void processAttached() {
		if(attachedTo == null) return;
		
		final boolean left = false;
		
		final float smooth = 25f;
		final float side = 80.0f;
		
		final float aX = attachedTo.getX();
		final float aY = attachedTo.getY();
		final float x = getX() + (left ? side : -side);
		final float y = getY();
		
		if(aX > x) {
			final float t = (aX - x) / smooth;
			moveX(t);
		} else 
		if(aX < x) {
			final float t = (x - aX) / smooth;
			moveX(-t);
		}
		if(aY > y) {
			final float t = (aY - y) / (smooth/2);
			moveY(t);
		} else
		if(aY < y) {
			final float t = (y - aY) / (smooth/2);
			moveY(-t);
		}
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public int getDirection() {
		return direction;
	}

	public boolean isAutoZoom() {
		return autoZoom;
	}

	public void setAutoZoom(boolean autoZoom) {
		this.autoZoom = autoZoom;
	}

	@Override
	public void setStartDirection(int dir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStartDirection() {
		return 1;
	}
}


























