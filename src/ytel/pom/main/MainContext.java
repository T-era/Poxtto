package ytel.pom.main;

import java.net.InetAddress;
import java.util.Random;

import ytel.pom.ApplicationContext;
import ytel.pom.common.timer.SystemTimer;
import ytel.pom.common.timer.TimerListener;
import ytel.pom.common.transport.RecieveService;
import ytel.pom.main.model.damage.DamageStock;
import ytel.pom.main.model.field.PomField;
import  ytel.pom.prepare.PrepareContext;
import ytel.pom.recievemode.GameMode;

public class MainContext {
	public static final Random RANDOM = new Random(System.currentTimeMillis());
	public static final int FALL_STEP = 3;
	public static final int CENTER_X = 3;
	public static final int WIDTH = 7;
	public static final int HEIGHT = 12;
	public static final int POM_SIZE_W = 30;
	public static final int POM_SIZE_H = 25;

	public final KeyDelegate keyDelegate;

	private final ApplicationContext parent;
	private final GameControl control;
	private final DamageStock damageStock;
	private final PomField pomField;
	private final SystemTimer timer;

	public MainContext(final ApplicationContext parent, PrepareContext pContext, InetAddress budyHost) {
		this.parent = parent;
		this.damageStock = new DamageStock();
		this.timer = new SystemTimer(new TimerListener() {
			@Override
			public void tickWithFall() {
				control.fallDown();

				parent.frame.repaint();
			}

			@Override
			public void tick() {
				control.eventTick();

				parent.frame.repaint();
			}
		});
		pomField = new PomField();
		this.control = new GameControl(pContext, pomField, parent.frame.nextGenerator, damageStock, parent.frame.mainPanel.scoreView, budyHost);
		this.keyDelegate = new KeyDelegate(control);
	}

	public void start(RecieveService recieve, InetAddress budyHost) {
		parent.frame.setGameMode(control);
		pomField.initialize(4);
		timer.start();
		recieve.setMode(new GameMode(budyHost, control));
	}

	public void doneAGame() {
		timer.stop();
		parent.gameDone();
	}
}
