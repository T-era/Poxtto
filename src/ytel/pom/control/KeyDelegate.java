package ytel.pom.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyDelegate implements KeyListener {
	private final MainControl main;
	private boolean shiftPushed;

	public KeyDelegate(MainControl main) {
		this.main = main;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) main.moveRight();
		else if (keyCode == KeyEvent.VK_LEFT) main.moveLeft();
		else if (keyCode == KeyEvent.VK_DOWN) main.moveDown();
		else if (keyCode == KeyEvent.VK_SPACE) main.moveReverse();
		else if (keyCode == KeyEvent.VK_SHIFT) shiftPushed = true;
		else if (keyCode == KeyEvent.VK_Z && shiftPushed) main.flashDef();
		else if (keyCode == KeyEvent.VK_X && shiftPushed) main.flashAtt();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) shiftPushed = false;
	}
}
