package ytel.pom.control;

import ytel.pom.model.Damage;

public interface MainControl {
	void moveRight();
	void moveLeft();
	void moveReverse();

	void flashAtt();
	void flashDef();

	void getDamage(Damage dmg);
}
