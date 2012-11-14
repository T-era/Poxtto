package ytel.pom.control;

import java.awt.Graphics;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import ytel.pom.control.ModeManager.ModeChangedListener;
import ytel.pom.control.game.GameSystemTimer;
import ytel.pom.gui.main.MainPanel;
import ytel.pom.gui.main.Pom;
import ytel.pom.model.Damage;

public class MainControlImpl implements MainControl, ModeChangedListener {
	private static final Random rnd = new Random(System.currentTimeMillis());

	private int fallingCountUp;
	private int fallingSpeed = 1;

	private final CopyOnWriteArrayList<Damage> damages;

	private GameSystemTimer timer;
	private MainControlEvent eventOn = null;
	private Pom[][] poms;
	private FallingPair fallingPair;
	private MainPanel panel;
	private ModeManager modeManager;

	public MainControlImpl() {
		damages = new CopyOnWriteArrayList<Damage>();
		poms = new Pom[HEIGHT + 5][WIDTH];
	}

	public void setModeManager(ModeManager modeManager) {
		this.modeManager = modeManager;
	}

	@Override
	public void init(InetAddress budyHost, MainPanel panel) {
		panel.init(budyHost);
		this.panel = panel;
	}

	@Override
	public void gotReady() {
		// TODO Auto-generated method stub
	}

	@Override
	public void started() {
		timer = new GameSystemTimer(this);
		timer.start();
		fallingPair = new FallingPair(3);
	}

	@Override
	public void ended() {
		timer.stop();
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
		// TODO
	}

	@Override
	public void flashDef() {
		// TODO
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
				if (poms[HEIGHT-1][WIDTH / 2] != null) {
					modeManager.done();
				}
			}
		} else {
			fallingCountUp ++;
			if (fallingCountUp > fallingSpeed) {
				moveDown();
				fallingCountUp = 0;
			}
		}
		if (panel != null) panel.repaint();
	}

	@Override
	public void drawPoms(Graphics g) {
		for (int cordX = 0; cordX < WIDTH; cordX ++) {
			for (int cordY = 0; cordY < HEIGHT; cordY ++) {
				Pom p = poms[cordY][cordX];
				if (p != null) {
					p.draw(g, cordX, cordY);
				}
			}
		}
		if (fallingPair != null)
			fallingPair.drawPair(g);
	}

	private void eventOccur() {
		List<Damage> list;
		synchronized(damages) {
			list = new ArrayList<Damage>(damages);
			damages.clear();
		}
		eventOn = new MainControlEvent(poms, list);
		poms[HEIGHT - fallingPair.y / POM_SIZE_H - 2][fallingPair.cordX] = fallingPair.p2;
		poms[HEIGHT - fallingPair.y / POM_SIZE_H - 1][fallingPair.cordX] = fallingPair.p1;

		fallingPair = new FallingPair(WIDTH /2);
	}

 	private class FallingPair {
		private int cordX;
		// 中間�Y座�
		private int y = 0;
		private Pom p1;
		private Pom p2;

		private FallingPair(int x) {
			int code1 = rnd.nextInt(AllColor.values().length);
			int code2 = rnd.nextInt(AllColor.values().length - 1);
			if (code2 >= code1) code2 ++;

			this.cordX = x;
			this.p1 = new Pom(AllColor.values()[code1]);
			this.p2 = new Pom(AllColor.values()[code2]);
		}

		public void MoveLeft() {
			int cordY = HEIGHT - y / POM_SIZE_H;
			if (cordX > 0
					&& poms[cordY-2][cordX - 1] == null) {
				cordX -= 1;
			}
		}
		public void MoveRiht() {
			int cordY = HEIGHT - y / POM_SIZE_H;
			if (cordX + 1 < WIDTH
					&& poms[cordY-2][cordX + 1] == null) {
				cordX += 1;
			}
		}
		public boolean MoveDown() {
			int cordY = HEIGHT - (y + FALL_STEP) / POM_SIZE_H - 2;
			if (cordY < 0
					|| poms[cordY][cordX] != null) {
				return true;
			} else {
				y += FALL_STEP;
				return false;
			}
		}
		public void MoveReverse() {
			Pom temp = p1;
			p1 = p2;
			p2 = temp;
		}

		public void drawPair(Graphics g) {
			p1.drawFalling(g, cordX * POM_SIZE_W, y - POM_SIZE_H);
			p2.drawFalling(g, cordX * POM_SIZE_W, y);
		}
	}
}
