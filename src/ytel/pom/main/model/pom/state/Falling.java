package ytel.pom.main.model.pom.state;

import java.awt.Color;
import java.awt.Graphics;

import ytel.pom.main.MainContext;
import ytel.pom.main.model.pom.PomColor;

public class Falling implements AState {
	private static final int EYE_WHITE = 11;
	private static final int EYE_BLACK = 5;
	private final int distY;

	public Falling(int distY) {
		this.distY = distY;
	}
	@Override
	public void drawImpl(Graphics g, int x, int y, PomColor c) {
		g.setColor(c.color);
		g.fillOval(x, distY + y, MainContext.POM_SIZE_W, MainContext.POM_SIZE_H);
		int eye1X = x + (MainContext.POM_SIZE_W) / 4;
		int eye2X = x + (MainContext.POM_SIZE_W) * 3 / 4;
		int eyeY = distY + y + (MainContext.POM_SIZE_H) * 2 / 5;

		g.setColor(Color.WHITE);
		g.fillOval(eye1X - EYE_WHITE/2, eyeY-EYE_WHITE/2, EYE_WHITE, EYE_WHITE);
		g.fillOval(eye2X - EYE_WHITE/2, eyeY-EYE_WHITE/2, EYE_WHITE, EYE_WHITE);
		g.setColor(Color.BLACK);
		g.fillOval(eye1X - EYE_BLACK/2, eyeY-EYE_BLACK/2, EYE_BLACK, EYE_BLACK);
		g.fillOval(eye2X - EYE_BLACK/2, eyeY-EYE_BLACK/2, EYE_BLACK, EYE_BLACK);
	}

}
