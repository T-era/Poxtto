package jp.gr.java_conf.t_era.pom.main.model.pom.state;

import java.awt.Graphics;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.model.pom.PomColor;

public class Erasing2 implements AState {
	private static final int WIDTH = 1;
	@Override
	public void drawImpl(Graphics g, int x, int y, PomColor c) {
		g.setColor(c.color);
		int centerX = x + MainContext.POM_SIZE_W / 2;
		int centerY = y + MainContext.POM_SIZE_H / 2;

		g.fillPolygon(
				new int[] { centerX - MainContext.POM_SIZE_W / 4 + WIDTH, centerX - 2 + WIDTH, centerX - 2 - WIDTH, centerX - MainContext.POM_SIZE_W / 4 - WIDTH},
				new int[] { centerY - MainContext.POM_SIZE_W / 4 - WIDTH, centerY - 2 - WIDTH, centerY - 2 + WIDTH, centerY - MainContext.POM_SIZE_W / 4 + WIDTH},
				4);
		g.fillPolygon(
				new int[] { centerX + 2 + WIDTH, centerX + MainContext.POM_SIZE_W / 4 + WIDTH, centerX + MainContext.POM_SIZE_W / 4 - WIDTH, centerX + 2 - WIDTH},
				new int[] { centerY + 2 - WIDTH, centerY + MainContext.POM_SIZE_W / 4 - WIDTH, centerY + MainContext.POM_SIZE_W / 4 + WIDTH, centerY + 2 + WIDTH},
				4);
		g.fillPolygon(
				new int[] { centerX - MainContext.POM_SIZE_W / 4 + WIDTH, centerX - 2 + WIDTH, centerX - 2 - WIDTH, centerX - MainContext.POM_SIZE_W / 4 - WIDTH},
				new int[] { centerY + MainContext.POM_SIZE_W / 4 + WIDTH, centerY + 2 + WIDTH, centerY + 2 - WIDTH, centerY + MainContext.POM_SIZE_W / 4 - WIDTH},
				4);
		g.fillPolygon(
				new int[] { centerX + 2 + WIDTH, centerX + MainContext.POM_SIZE_W / 4 + WIDTH, centerX + MainContext.POM_SIZE_W / 4 - WIDTH, centerX + 2 - WIDTH},
				new int[] { centerY - 2 + WIDTH, centerY - MainContext.POM_SIZE_W / 4 + WIDTH, centerY - MainContext.POM_SIZE_W / 4 - WIDTH, centerY - 2 - WIDTH},
				4);
	}
}
