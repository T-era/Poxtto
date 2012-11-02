package ytel.pom.gui.main;

import java.awt.Color;
import java.awt.Graphics;

import ytel.pom.control.MainControl;
import ytel.pom.control.MainControl.AllColor;

public class Pom {
	private final AllColor c;
	private State state;
	public Pom(AllColor c) {
		this.c = c;
		this.state = State.Normal;
	}
	public AllColor getColor() {
		return c;
	}

	public void collapse() {
		this.state = State.Erase1;
	}
	public void move() {
		this.state = next();
	}
	private State next() {
		switch (this.state) {
		case Normal: return State.Normal;
		case Erase1: return State.Erase2;
		case Erase2: return State.Erase3;
		case Erase3: return State.Fall1;
		case Fall1: return State.Fall2;
		case Fall2: return State.Fall3;
		case Fall3: throw new IllegalStateException();
		}
		throw new IllegalStateException();
	}
	
	public void draw(Graphics g, int cordX, int cordY) {
		if (cordY < MainControl.HEIGHT)
		{
			int y = cordToDrawY(cordY) + (int)(MainControl.POM_SIZE_H * state.down);
			int x = cordToDrawX(cordX);
			drawImpl(g, x, y);
		}
	}
	
	private static final int EYE_WHITE = 11;
	private static final int EYE_BLACK = 7;
	private void drawImpl(Graphics g, int x, int y) {
		g.setColor(c.getColor());
		g.fillOval(x, y, MainControl.POM_SIZE_W, MainControl.POM_SIZE_H);
		int eye1X = x + (MainControl.POM_SIZE_W) / 4;
		int eye2X = x + (MainControl.POM_SIZE_W) * 3 / 4;
		int eyeY = y + (MainControl.POM_SIZE_H) * 2 / 5;
		g.setColor(Color.WHITE);
		g.fillOval(eye1X - EYE_WHITE/2, eyeY-EYE_WHITE/2, EYE_WHITE, EYE_WHITE);
		g.fillOval(eye2X - EYE_WHITE/2, eyeY-EYE_WHITE/2, EYE_WHITE, EYE_WHITE);
		g.setColor(Color.BLACK);
		g.fillOval(eye1X - EYE_BLACK/2, eyeY-EYE_BLACK/2, EYE_BLACK, EYE_BLACK);
		g.fillOval(eye2X - EYE_BLACK/2, eyeY-EYE_BLACK/2, EYE_BLACK, EYE_BLACK);
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

	public static enum State {
		Normal(0),
		Erase1(0),
		Erase2(0),
		Erase3(0),
		Fall1(0.1),
		Fall2(0.3),
		Fall3(0.8);
		private double down;
		private State(double down) {
			this.down = down;
		}
	}
	public int cordToDrawX(int cordX) {
		return cordX * MainControl.POM_SIZE_W;
	}
	public int drawToCordX(int x) {
		return x / MainControl.POM_SIZE_W;
	}
	public int cordToDrawY(int cordY) {
		return ((MainControl.HEIGHT-1) - cordY) * MainControl.POM_SIZE_H;
	}
	public int drawToCordy(int y) {
		return (MainControl.HEIGHT-1) - y / MainControl.POM_SIZE_W;
	}
}
