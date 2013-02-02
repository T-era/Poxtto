package ytel.pom.main.model.damage;

import java.io.Serializable;

public class Damage implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5831031387918172907L;

	private final int score;
	public Damage() {
		this(0);
	}
	public Damage(int score) {
		this.score = score;
	}
	public int getLine() {
		return scoreToLine(this.score);
	}

	public static int scoreToLine(int score) {
		if (score > 999) {
			return 10;
		} else {
			return score / 100;
		}
	}
}
