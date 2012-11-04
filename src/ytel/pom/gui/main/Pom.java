package ytel.pom.gui.main;

import java.awt.Graphics;
import java.net.NoRouteToHostException;

import ytel.pom.control.MainControl;
import ytel.pom.control.MainControl.AllColor;
import ytel.pom.control.game.pomstate.AState;
import ytel.pom.control.game.pomstate.Erasing1;
import ytel.pom.control.game.pomstate.Erasing2;
import ytel.pom.control.game.pomstate.Erasing3;
import ytel.pom.control.game.pomstate.Normal;

public class Pom {
	private static final AState NORMAL = new Normal();
	private static final AState ERASING1 = new Erasing1();
	private static final AState ERASING2 = new Erasing2();
	private static final AState ERASING3 = new Erasing3();

	private final AllColor c;
	private AState state;
	public Pom(AllColor c) {
		this.c = c;
		this.state = NORMAL;
	}
	public AllColor getColor() {
		return c;
	}

	public void setState(AState newState) {
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
			throw new RuntimeException();
		}
	}
	
	public void draw(Graphics g, int cordX, int cordY) {
		if (cordY < MainControl.HEIGHT)
		{
			int y = cordToDrawY(cordY);
			int x = cordToDrawX(cordX);
			drawImpl(g, x, y);
		}
	}
	
	private void drawImpl(Graphics g, int x, int y) {
		state.drawImpl(g, x, y, c);
	}
	
	/**
	 * 
	 * @param g
	 * @param x 描画座標
	 * @param y 描画座標 (Falling Pair は論理座標系より細かい制御だから。)
	 */
	public void drawFalling(Graphics g, int x, int y) {
		drawImpl(g, x, y);
	}

	public int cordToDrawX(int cordX) {
		return cordX * MainControl.POM_SIZE_W;
	}
	public int cordToDrawY(int cordY) {
		return ((MainControl.HEIGHT-1) - cordY) * MainControl.POM_SIZE_H;
	}
}
