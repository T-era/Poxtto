package ytel.pom.control.game.pomstate;

import java.awt.Graphics;

import ytel.pom.control.MainControl.AllColor;

public interface AState {
	void drawImpl(Graphics g, int x, int y, AllColor c);
}
