package jp.gr.java_conf.t_era.pom.main.model.pom;

import java.awt.Graphics;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.model.pom.state.AState;
import jp.gr.java_conf.t_era.pom.main.model.pom.state.Erasing1;
import jp.gr.java_conf.t_era.pom.main.model.pom.state.Erasing2;
import jp.gr.java_conf.t_era.pom.main.model.pom.state.Erasing3;
import jp.gr.java_conf.t_era.pom.main.model.pom.state.Falling;
import jp.gr.java_conf.t_era.pom.main.model.pom.state.Normal;

public class Pom {
	private static final AState NORMAL = new Normal();
	private static final AState ERASING1 = new Erasing1();
	private static final AState ERASING2 = new Erasing2();
	private static final AState ERASING3 = new Erasing3();
	private static final AState FALLING1 = new Falling(MainContext.POM_SIZE_H * 1 / 10);
	private static final AState FALLING2 = new Falling(MainContext.POM_SIZE_H * 4 / 10);
	private static final AState FALLING3 = new Falling(MainContext.POM_SIZE_H * 9 / 10);
	private static final AState FALLING4 = new Falling(MainContext.POM_SIZE_H * 4 / 10);
	private static final AState FALLING5 = new Falling(MainContext.POM_SIZE_H * 9 / 10);

	public final PomColor color;
	private AState state;

	public static Pom createInstance(PomColor color) {
		return new Pom(color);
	}

	private Pom(PomColor color) {
		this.color = color;
		this.state = NORMAL;
	}

	public final void setState(AState newState) {
		this.state = newState;
	}
	public void setErasing() {
		if (state == NORMAL) {
			state = ERASING1;
		} else if (state == ERASING1) {
			state = ERASING2;
		} else if (state == ERASING2) {
			state = ERASING3;
		} else {
			throw new RuntimeException(state.toString());
		}
	}
	public final boolean fall(boolean continueFalling) {
		if (state == NORMAL) {
			if (continueFalling) {
				state = FALLING4;
			} else {
				state = FALLING1;
			}
		} else if (state == FALLING1) {
			state = FALLING2;
		} else if (state == FALLING2) {
			state = FALLING3;
		} else if (state == FALLING3) {
//			state = NORMAL;
			return true;
		} else if (state == FALLING4) {
			state = FALLING5;
		} else if (state == FALLING5) {
//			state = NORMAL;
			return true;
		} else {
			throw new IllegalStateException();
		}
		return false;
	}
	public final boolean isFalling() {
		return state != NORMAL;
	}
	public final void setNotFalling() {
		state = NORMAL;
	}

	/**
	 * 通常時の描画
	 * @param g
	 * @param x 論理座標
	 * @param y 論理座標
	 */
	public void draw(Graphics g, int cordX, int cordY) {
		if (cordY < MainContext.HEIGHT)
		{
			int y = cordToDrawY(cordY);
			int x = cordToDrawX(cordX);
			drawImpl(g, x, y);
		}
	}

	/**
	 * 落下時の描画
	 * @param g
	 * @param x 描画座標
	 * @param y 描画座標 (Falling Pair は論理座標系より細かい制御だから。)
	 */
	public void drawFalling(Graphics g, int x, int y) {
		drawImpl(g, x, y);
	}

	private void drawImpl(Graphics g, int x, int y) {
		state.drawImpl(g, x, y, color);
	}

	private static int cordToDrawX(int cordX) {
		return cordX * MainContext.POM_SIZE_W;
	}
	public static int cordToDrawY(int cordY) {
		return ((MainContext.HEIGHT-1) - cordY) * MainContext.POM_SIZE_H;
	}

	@Override
	public final String toString() {
		return color.toString();
	}
}
