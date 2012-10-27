package ytel.pom.control;

import java.awt.Graphics;

import ytel.pom.model.Damage;

public interface MainControl {
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
