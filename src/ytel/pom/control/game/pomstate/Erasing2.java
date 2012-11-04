package ytel.pom.control.game.pomstate;

import java.awt.Graphics;

import ytel.pom.control.MainControl;
import ytel.pom.control.MainControl.AllColor;

public class Erasing2 implements AState {
	private static final int WIDTH = 1;
	@Override
	public void drawImpl(Graphics g, int x, int y, AllColor c) {
		g.setColor(c.getColor());
		int centerX = x + MainControl.POM_SIZE_W / 2;
		int centerY = y + MainControl.POM_SIZE_H / 2;

		g.fillPolygon(
				new int[] { centerX - MainControl.POM_SIZE_W / 4 + WIDTH, centerX - 2 + WIDTH, centerX - 2 - WIDTH, centerX - MainControl.POM_SIZE_W / 4 - WIDTH},
				new int[] { centerY - MainControl.POM_SIZE_W / 4 - WIDTH, centerY - 2 - WIDTH, centerY - 2 + WIDTH, centerY - MainControl.POM_SIZE_W / 4 + WIDTH},
				4);
		g.fillPolygon(
				new int[] { centerX + 2 + WIDTH, centerX + MainControl.POM_SIZE_W / 4 + WIDTH, centerX + MainControl.POM_SIZE_W / 4 - WIDTH, centerX + 2 - WIDTH},
				new int[] { centerY + 2 - WIDTH, centerY + MainControl.POM_SIZE_W / 4 - WIDTH, centerY + MainControl.POM_SIZE_W / 4 + WIDTH, centerY + 2 + WIDTH},
				4);
		g.fillPolygon(
				new int[] { centerX - MainControl.POM_SIZE_W / 4 + WIDTH, centerX - 2 + WIDTH, centerX - 2 - WIDTH, centerX - MainControl.POM_SIZE_W / 4 - WIDTH},
				new int[] { centerY + MainControl.POM_SIZE_W / 4 + WIDTH, centerY + 2 + WIDTH, centerY + 2 - WIDTH, centerY + MainControl.POM_SIZE_W / 4 - WIDTH},
				4);
		g.fillPolygon(
				new int[] { centerX + 2 + WIDTH, centerX + MainControl.POM_SIZE_W / 4 + WIDTH, centerX + MainControl.POM_SIZE_W / 4 - WIDTH, centerX + 2 - WIDTH},
				new int[] { centerY - 2 + WIDTH, centerY - MainControl.POM_SIZE_W / 4 + WIDTH, centerY - MainControl.POM_SIZE_W / 4 - WIDTH, centerY - 2 - WIDTH},
				4);
	}
}
