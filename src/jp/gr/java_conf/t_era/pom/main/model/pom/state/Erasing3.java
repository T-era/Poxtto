package jp.gr.java_conf.t_era.pom.main.model.pom.state;

import java.awt.Graphics;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.model.pom.PomColor;

public class Erasing3 implements AState {
	@Override
	public void drawImpl(Graphics g, int x, int y, PomColor c) {
		int centerX = x + MainContext.POM_SIZE_W / 2;
		int centerY = y + MainContext.POM_SIZE_H / 2;

		g.setColor(c.color);
		g.fillOval(centerX - MainContext.POM_SIZE_W / 5, centerY - MainContext.POM_SIZE_H / 5, MainContext.POM_SIZE_W * 2 / 5, MainContext.POM_SIZE_H * 2 / 5);
	}
}
