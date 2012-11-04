package ytel.pom.control.game.pomstate;

import java.awt.Graphics;

import ytel.pom.control.MainControl;
import ytel.pom.control.MainControl.AllColor;

public class Erasing3 implements AState {
	@Override
	public void drawImpl(Graphics g, int x, int y, AllColor c) {
		int centerX = x + MainControl.POM_SIZE_W / 2;
		int centerY = y + MainControl.POM_SIZE_H / 2;
		
		g.setColor(c.getColor());
		g.fillOval(centerX - MainControl.POM_SIZE_W / 5, centerY - MainControl.POM_SIZE_H / 5, MainControl.POM_SIZE_W * 2 / 5, MainControl.POM_SIZE_H * 2 / 5);
	}
}
