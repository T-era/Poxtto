package ytel.pom.control;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import ytel.pom.gui.main.Pom;
import ytel.pom.model.Damage;

public class MainControlImpl implements MainControl {
	private static final Random rnd = new Random(System.currentTimeMillis());

	private static final Color[] ALL_COLOR = { Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN };

	public static final int WIDTH = 7;
	public static final int HEIGHT = 12;

	private int fallingCountUp;
	private int fallingSpeed = 10;

	private final int pomSize;
	private final CopyOnWriteArrayList<Damage> damages;

	private MainControlEvent eventOn = null;
	private Pom[][] poms;
	private FallingPair fallingPair;

	public MainControlImpl(int pomSize) {
		this.pomSize = pomSize;
		damages = new CopyOnWriteArrayList<Damage>();
		poms = new Pom[WIDTH][HEIGHT];
	}

	@Override
	public void moveRight() {
		if (eventOn == null) {
			fallingPair.MoveRiht();
		}
	}

	@Override
	public void moveLeft() {
		if (eventOn == null) {
			fallingPair.MoveLeft();
		}
	}

	@Override
	public void moveDown() {
		if (eventOn == null) {
			if (fallingPair.MoveDown()) {
				eventOccur();
			}
		}
	}

	@Override
	public void moveReverse() {
		if (eventOn == null) {
			fallingPair.MoveReverse();
		}
	}

	@Override
	public void flashAtt() {
	}

	@Override
	public void flashDef() {
	}

	@Override
	public void getDamage(Damage dmg) {
		damages.add(dmg);
	}

	@Override
	public void tick() {
		if (eventOn != null) {
			boolean done = eventOn.next();
			if (done) {
				eventOn = null;
			}
		} else {
			fallingCountUp ++;
			if (fallingCountUp > fallingSpeed) {
				moveDown();
				fallingCountUp = 0;
			}
		}
	}

	@Override
	public void drawPoms(Graphics g) {
		for (int x = 0; x < WIDTH; x ++) {
			for (int y = 0; y < HEIGHT; y ++) {
				Pom p = poms[x][y];
				if (p != null) {
					p.draw(g, x, y, pomSize);
				}
			}
		}
		if (fallingPair != null)
			fallingPair.drawPair(g);
	}

	private void eventOccur() {
		List<Damage> list;
		synchronized(damages) {
			list = new ArrayList<Damage>();
			damages.clear();
		}
		eventOn = new MainControlEvent(poms, list);
		poms[fallingPair.cordX][fallingPair.y / pomSize] = fallingPair.p2;
		poms[fallingPair.cordX][fallingPair.y / pomSize - 1] = fallingPair.p1;

		fallingPair = new FallingPair(WIDTH /2);
	}

	private class FallingPair {
		private int cordX;
		// 中間のY座標
		private int y = 0;
		private Pom p1;
		private Pom p2;

		private FallingPair(int x) {
			int code1 = rnd.nextInt(ALL_COLOR.length);
			int code2 = rnd.nextInt(ALL_COLOR.length - 1);
			if (code2 == code1) code2 ++;

			this.cordX = x * pomSize;
			this.p1 = new Pom(ALL_COLOR[code1]);
			this.p2 = new Pom(ALL_COLOR[code2]);
		}

		public void MoveLeft() {
			int cordY = y / pomSize;
			if (cordX > pomSize
					&& poms[cordX - 1][cordY] == null) {
				cordX -= pomSize;
			}
		}
		public void MoveRiht() {
			int cordY = y / pomSize;
			if ((cordX + 1) * pomSize > pomSize * WIDTH
					&& poms[cordX + 1][cordY] == null) {
				cordX += pomSize;
			}
		}
		public boolean MoveDown() {
			int cordY = (y + 1) / pomSize;
			if (cordY == HEIGHT - 1
					|| poms[cordX][cordY] != null) {
				return true;
			} else {
				y += 1;
				return false;
			}
		}
		public void MoveReverse() {
			Pom temp = p1;
			p1 = p2;
			p2 = temp;
		}

		public void drawPair(Graphics g) {
			p1.draw(g, cordX * pomSize, y - pomSize, pomSize);
			p2.draw(g, cordX * pomSize, y, pomSize);
		}
	}
}
