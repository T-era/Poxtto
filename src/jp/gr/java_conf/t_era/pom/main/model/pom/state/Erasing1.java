package jp.gr.java_conf.t_era.pom.main.model.pom.state;

import java.awt.Color;
import java.awt.Graphics;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.model.pom.PomColor;

public class Erasing1 implements AState {
	private static final int EYE_WHITE = 11;
	private static final int EYE_BLACK = 7;
	@Override
	public void drawImpl(Graphics g, int x, int y, PomColor c) {
		g.setColor(c.color);
		g.fillOval(x + 2, y + 2, MainContext.POM_SIZE_W - 4, MainContext.POM_SIZE_H - 4);
		int eye1X = x + (MainContext.POM_SIZE_W) / 4;
		int eye2X = x + (MainContext.POM_SIZE_W) * 3 / 4;
		int eyeY = y + (MainContext.POM_SIZE_H) * 2 / 5;

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
