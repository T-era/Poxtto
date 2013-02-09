package jp.gr.java_conf.t_era.pom.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyDelegate implements KeyListener {
	private final GameControl io;
	private boolean shiftPushed;

	public KeyDelegate(GameControl in) {
		this.io = in;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SHIFT) {
			shiftPushed = true;
		}

		if (keyCode == KeyEvent.VK_RIGHT) io.moveRight();
		else if (keyCode == KeyEvent.VK_LEFT) io.moveLeft();
		else if (keyCode == KeyEvent.VK_DOWN) io.moveDown();
		else if (keyCode == KeyEvent.VK_SPACE) io.moveReverse();
		else if (keyCode == KeyEvent.VK_SHIFT) shiftPushed = true;
		else if (keyCode == KeyEvent.VK_Z && shiftPushed) io.flashDef();
		else if (keyCode == KeyEvent.VK_X && shiftPushed) io.flashAtt();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			shiftPushed = false;
		}
	}

	public boolean isShiftPushed() {
		return shiftPushed;
	}
}
