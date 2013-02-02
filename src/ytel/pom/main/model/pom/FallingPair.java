package ytel.pom.main.model.pom;

import java.awt.Graphics;
import java.util.Random;

import ytel.pom.main.MainContext;
import ytel.pom.main.model.field.PomField;

public class FallingPair {
	private final PomField field;
	private int cordX;
	/** 中間Y座標 **/
	private int y = 0;
	private Pom p1;
	private Pom p2;

	FallingPair(int x, Random rnd, PomField field) {
		int code1 = rnd.nextInt(PomColor.values().length);
		int code2 = rnd.nextInt(PomColor.values().length - 1);
		if (code2 >= code1) code2 ++;

		this.cordX = x;
		this.p1 = Pom.createInstance(PomColor.values()[code1]);
		this.p2 = Pom.createInstance(PomColor.values()[code2]);
		this.field = field;
	}

	public void MoveLeft() {
		int cordY = MainContext.HEIGHT - y / MainContext.POM_SIZE_H;
		if (cordX > 0
				&& field.at(cordX - 1, cordY - 2) == null) {
			cordX -= 1;
		}
	}
	public void MoveRiht() {
		int cordY = MainContext.HEIGHT - y / MainContext.POM_SIZE_H;
		if (cordX + 1 < MainContext.WIDTH
				&& field.at(cordX + 1, cordY - 2) == null) {
			cordX += 1;
		}
	}
	/**
	 *
	 * @return 接地したらtrue
	 */
	public boolean MoveDown() {
		int cordNextY = MainContext.HEIGHT - (y + MainContext.FALL_STEP) / MainContext.POM_SIZE_H - 2;
		if (cordNextY < 0
				|| field.at(cordX, cordNextY) != null) {
			field.setAt(cordX, cordNextY + 1, p2);
			field.setAt(cordX, cordNextY + 2, p1);
			return true;
		} else {
			y += MainContext.FALL_STEP;
			return false;
		}
	}
	public void MoveReverse() {
		Pom temp = p1;
		p1 = p2;
		p2 = temp;
	}

	public void drawPair(Graphics g) {
		p1.drawFalling(g, cordX * MainContext.POM_SIZE_W, y - MainContext.POM_SIZE_H);
		p2.drawFalling(g, cordX * MainContext.POM_SIZE_W, y);
	}
	public void drawPairInLeft(Graphics g, int y) {
		p1.drawFalling(g, 0, y);
		p2.drawFalling(g, 0, y + MainContext.POM_SIZE_H);
	}

	public int getCordX() {
		return cordX;
	}

	public int getY() {
		return y;
	}

	Pom getP1() {
		return p1;
	}

	Pom getP2() {
		return p2;
	}
}
