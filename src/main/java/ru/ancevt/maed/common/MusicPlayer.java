package ru.ancevt.maed.common;

import ru.ancevt.d2d2.sound.Sound;

public class MusicPlayer {
	
	private static MusicPlayer instance;
	
	public static final MusicPlayer getInstance() {
		return instance == null ? instance = new MusicPlayer() : instance;
	}
	
	private Sound sound;
	private boolean enabled;
	
	private MusicPlayer() {
		setEnabled(true);
	}
	
	public final void playMusic(final Sound sound) {
		if(!isEnabled() || sound == null) return;
		
		stopMusic();
		sound.setLoop(true);
		sound.play();
		this.sound = sound;
	}
	
	public final void stopMusic() {
		if(sound != null) 
			sound.stop();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
