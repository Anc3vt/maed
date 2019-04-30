package ru.ancevt.maed.gameobject.weapon;

import ru.ancevt.d2d2.display.FrameSet;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.gameobject.Actor;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.IDamaging;
import ru.ancevt.maed.gameobject.ITight;
import ru.ancevt.maed.world.World;

public class DefaultBullet extends Bullet implements IDamaging {

	private static final int DAMAGING_POWER = 10;
	private static final float SPEED = 4.0f;
	
	private Sprite sprite;
	private FrameSet frameSetDestroy;
	
	public DefaultBullet() {
		super();
		sprite = new Sprite("wp-default-bullet");
		sprite.setXY(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		final Sprite[] frames = new Sprite[] {
			new Sprite("wp-default-bullet-d0"),
			new Sprite("wp-default-bullet-d1"),
			new Sprite("wp-default-bullet-d2"),
			new Sprite("wp-default-bullet-d3"),
		};
		
		frameSetDestroy = new FrameSet(frames) {
			public void onAnimationComplete() {
				final Bullet _this = DefaultBullet.this;
				World.getWorld().removeGameObject(_this, false);
			};
		};
		
		frameSetDestroy.setSlowing(3);
		
		frameSetDestroy.setXY(-16, -16);
	}
	
	@Override
	public void onEachFrame() {
		if(sprite.hasParent())
			moveX(getDirection() * SPEED);
		
		super.onEachFrame();
	}
	
	@Override
	public int getDamagingPower() {
		return DAMAGING_POWER;
	}

	@Override
	public void prepare() {
		setDirection(getDamagingOwnerActor().getDirection());
		setCollisionEnabled(true);
		add(sprite);
		
		if(frameSetDestroy.hasParent()) remove(frameSetDestroy);
		
		frameSetDestroy.stop();
		frameSetDestroy.goToFrame(0);
	}

	@Override
	public void destroy() {
		setCollisionEnabled(false);
		
		if(sprite.hasParent())
			remove(sprite);
		
		add(frameSetDestroy);
		frameSetDestroy.play();
	}

	@Override
	public void onCollide(ICollisioned collideWith) {
		if(collideWith instanceof ITight) 
			destroy();
	}

	@Override
	public void setDamagingOwnerActor(Actor character) {
		
	}

	@Override
	public Actor getDamagingOwnerActor() {
		return null;
	}

	@Override
	public void setStartDirection(int dir) {
	}

	@Override
	public int getStartDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	
}
