package ytel.pom.control;

import java.awt.Color;
import java.awt.Graphics;
import java.net.InetAddress;

import ytel.pom.gui.main.MainPanel;
import ytel.pom.model.Damage;

public interface MainControl {
	public static final int FALL_STEP = 3;
	public static final int WIDTH = 7;
	public static final int HEIGHT = 12;
	public static final int POM_SIZE_W = 30;
	public static final int POM_SIZE_H = 25;
	public enum AllColor {
		BLUE(new Color(32, 96, 255)),
		RED(new Color(255, 64, 64)),
		YELLOW(Color.YELLOW),
		GREEN (Color.GREEN);
	
		private final Color c;
		private AllColor(Color c) {
			this.c = c;
		}
		public Color getColor() { return c; }
	};

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
