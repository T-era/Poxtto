package jp.gr.java_conf.t_era.pom.main.model.field;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.model.pom.Pom;
import jp.gr.java_conf.t_era.pom.main.model.pom.PomColor;

public class PomField {
	private final Pom[][] poms;
	public final int heightAll;

	public PomField() {
		heightAll = MainContext.HEIGHT + 5;
		poms = new Pom[heightAll][MainContext.HEIGHT];
	}
	public Pom at(int x, int y) {
		return poms[y][x];
	}
	public boolean isOverflow() {
		return atCenter() != null;
	}

	private Pom atCenter() {
		return poms[MainContext.HEIGHT - 1][MainContext.WIDTH / 2];
	}
	public void setAt(int x, int y, Pom pom) {
		poms[y][x] = pom;
	}

	@Override
	public String toString() {
		return toString("P", poms);
	}
	public static <T> String toString(String header, T[][] a) {
		StringBuilder sb = new StringBuilder(header);
		for (int i = 0; i < a.length; i ++) {
			for (int j = 0; j < a[i].length; j ++) {
				sb.append(a[i][j]);
			}
			sb.append("\n");
		}
		return new String(sb);
	}

	public void initialize(int line) {
		initializeLines(line);
	}
	private void initializeLines(int line) {
		new FieldInitializer(line).attatch(poms);
	}

	public Set<PomXY> getUnder(int line) {
		Set<PomXY> set = new HashSet<PomXY>();
		for (int y = 0; y < line; y ++) {
			for (int x = 0; x < MainContext.WIDTH; x ++) {
				Pom pom = poms[y][x];
				if (pom != null) {
					set.add(new PomXY(pom, x, y));
				}
			}
		}
		return set;
	}

	private Rising riseMode = null;
	public void draw(Graphics g) {
		for (int y = 0; y < MainContext.HEIGHT; y ++) {
			for (int x = 0; x < MainContext.WIDTH; x ++) {
				if (riseMode == null) {
					if (poms[y][x] != null) {
						poms[y][x].draw(g, x, y);
					}
				} else {
					riseMode.drawPom(g, x, y);
				}
			}
		}
	}

	public void riseALine(GroupingStructer gs) {
		riseMode = new Rising(gs);
	}
	public void notRising() {
		riseMode = null;
	}
	public void setRise(double d) {
		riseMode.d = d;
	}
	private class Rising {
		private double d;
		private Rising(GroupingStructer gs) {
			// 1行上昇
			for (int x = 0; x < MainContext.WIDTH; x ++) {
				for (int y = heightAll - 1; y > 0; y --) {
					poms[y][x] = poms[y - 1][x];
				}
			}

			PomColor previous2 = null;
			PomColor previous = null;
			for (int x = 0; x < MainContext.WIDTH; x ++) {
				PomColor now = PomColor.select(MainContext.RANDOM, gs.getIgnoreForRise(x, previous, previous2));
				poms[0][x] = Pom.createInstance(now);
				previous2 = previous;
				previous = now;
			}
		}
		private void drawPom(Graphics g, int cordX, int cordY) {
			int x = cordX * MainContext.POM_SIZE_W;
			int y = Pom.cordToDrawY(cordY);
			int dy = (int)(MainContext.POM_SIZE_H * d);
			Pom pom = poms[cordY][cordX];
			if (pom != null) pom.drawFalling(g, x, y - dy);
		}
	}
}
