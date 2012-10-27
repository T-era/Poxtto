package ytel.pom.gui.main;

import java.awt.Color;
import java.awt.Graphics;

public class Pom {
	private final Color c;
	private State state;
	public Pom(Color c) {
		this.c = c;
		this.state = State.Normal;
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
		}
		throw new IllegalStateException();
	}
	public void draw(Graphics g, int x, int y, int pomSize) {
		g.setColor(c);
		int drawY = y + (int)(pomSize * state.down);
		g.fillOval(x, drawY, pomSize, pomSize);
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
}
