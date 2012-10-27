package ytel.pom.control;

import java.awt.Graphics;

import ytel.pom.model.Damage;

public interface MainControl {
	public static final int WIDTH = 7;
	public static final int HEIGHT = 12;
	public static final int POM_SIZE_W = 30;
	public static final int POM_SIZE_H = 25;

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
