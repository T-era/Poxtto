package ytel.pom.control.game.pomstate;

import java.awt.Color;
import java.awt.Graphics;

import ytel.pom.control.MainControl;
import ytel.pom.control.MainControl.AllColor;

public class Erasing1 implements AState {
	private static final int EYE_WHITE = 11;
	private static final int EYE_BLACK = 7;
	@Override
	public void drawImpl(Graphics g, int x, int y, AllColor c) {
		g.setColor(c.getColor());
		g.fillOval(x + 2, y + 2, MainControl.POM_SIZE_W - 4, MainControl.POM_SIZE_H - 4);
		int eye1X = x + (MainControl.POM_SIZE_W) / 4;
		int eye2X = x + (MainControl.POM_SIZE_W) * 3 / 4;
		int eyeY = y + (MainControl.POM_SIZE_H) * 2 / 5;

		g.setColor(Color.WHITE);
		g.fillOval(eye1X - EYE_WHITE/2, eyeY-EYE_WHITE/2, EYE_WHITE, EYE_WHITE);
		g.fillOval(eye2X - EYE_WHITE/2, eyeY-EYE_WHITE/2, EYE_WHITE, EYE_WHITE);
		g.setColor(Color.BLACK);
		g.drawLine(eye1X - EYE_BLACK/2, eyeY-EYE_BLACK/2, eye1X + EYE_BLACK / 2, eyeY + EYE_BLACK / 2);
		g.drawLine(eye1X - EYE_BLACK/2, eyeY+EYE_BLACK/2, eye1X + EYE_BLACK / 2, eyeY - EYE_BLACK / 2);
		g.drawLine(eye2X - EYE_BLACK/2, eyeY-EYE_BLACK/2, eye2X + EYE_BLACK / 2, eyeY + EYE_BLACK / 2);
		g.drawLine(eye2X - EYE_BLACK/2, eyeY+EYE_BLACK/2, eye2X + EYE_BLACK / 2, eyeY - EYE_BLACK / 2);
	}
}
