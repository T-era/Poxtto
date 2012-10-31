package ytel.pom.control;

import java.awt.Color;
import java.awt.Graphics;
import java.net.InetAddress;

import ytel.pom.gui.main.MainPanel;
import ytel.pom.model.Damage;

public interface MainControl {
	public static final int WIDTH = 7;
	public static final int HEIGHT = 12;
	public static final int POM_SIZE_W = 30;
	public static final int POM_SIZE_H = 25;
	public static final Color[] ALL_COLOR = { Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN };

	void init(InetAddress budyHost, MainPanel panel);
	void moveRight();
	void moveLeft();
	void moveDown();
	void moveReverse();

	void flashAtt();
	void flashDef();

	void getDamage(Damage dmg);
	void tick();
	void drawPoms(Graphics g);
}
