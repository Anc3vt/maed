package ru.ancevt.maed.arming;

import ru.ancevt.d2d2.display.FrameSet;
import ru.ancevt.d2d2.display.Sprite;
import ru.ancevt.maed.gameobject.ICollisioned;
import ru.ancevt.maed.gameobject.ITight;
import ru.ancevt.maed.world.World;

public class DefaultBullet extends Bullet  {
	
	private static final float SPEED = 4f;
	
	private Sprite sprite;
	private FrameSet explosionFrameSet;
	private boolean fly;
	private boolean killed;
	
	public DefaultBullet() {
		setCollisionArea(-2, -2, 4, 4);
		setDamagingPower(5);
		
		fly = true;
		
		sprite = new Sprite("wp-default-bullet");
		sprite.setXY(-sprite.getWidth()/2, -sprite.getHeight()/2);
	
		explosionFrameSet = new FrameSet(new Sprite[] {
			new Sprite("wp-default-bullet-d0"),
			new Sprite("wp-default-bullet-d1"),
			new Sprite("wp-default-bullet-d2"),
			new Sprite("wp-default-bullet-d3"),
		}) {
			@Override
			public void onAnimationComplete() {
				// removeFromParent();
				World.getWorld().removeGameObject(DefaultBullet.this, false);
				super.onAnimationComplete();
			}
		};
		explosionFrameSet.setLoop(false);
	}
	
	public void prepare() {
		
		add(sprite);
		fly = true;
		killed = false;
		explosionFrameSet.goToFrame(0);
		explosionFrameSet.stop();
		explosionFrameSet.removeFromParent();
		explosionFrameSet.setXY(-explosionFrameSet.getWidth()/2, -explosionFrameSet.getHeight()/2);
	}
	
	@Override
	public void onEachFrame() {
		if(fly) moveX(getDirection() * SPEED);
		super.onEachFrame();
	}
	
	@Override
	public void onCollide(ICollisioned collideWith) {
		if(!killed && collideWith instanceof ITight) {
			add(explosionFrameSet);
			explosionFrameSet.play();
			fly = false;
			killed = true;
			sprite.removeFromParent();
		}
	}


}
