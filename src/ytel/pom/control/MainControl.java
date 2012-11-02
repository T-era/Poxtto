package ytel.pom.control;

import java.awt.Color;
import java.awt.Graphics;

import ytel.pom.gui.main.MainPanel;
import ytel.pom.model.Damage;

public interface MainControl {
	public static final int WIDTH = 7;
	public static final int HEIGHT = 12;
	public static final int POM_SIZE_W = 30;
	public static final int POM_SIZE_H = 25;
	public enum AllColor {
		BLUE(Color.BLUE),
		RED(Color.RED),
		YELLOW(Color.YELLOW),
		GREEN (Color.GREEN);
	
		private final Color c;
		private AllColor(Color c) {
			this.c = c;
		}
		public Color getColor() { return c; }
	};

	void init(String budyHost, MainPanel panel);
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
