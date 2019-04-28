package ru.ancevt.maed.editor;

public interface EditorControllerListener {
	void editorControllerKeyUp(int keyCode, boolean shift, boolean control, boolean alt);
	void editorControllerKeyDown(int keyCode, boolean shift, boolean control, boolean alt);
}
