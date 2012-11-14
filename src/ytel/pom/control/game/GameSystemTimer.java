package ytel.pom.control.game;

import java.util.Timer;
import java.util.TimerTask;

import ytel.pom.control.MainControl;

public class GameSystemTimer {
	private final MainControl control;
	private final Timer timer;

	public GameSystemTimer(MainControl control) {
		this.control = control;
		this.timer = new Timer();
	}
	public void start() {
		timer.schedule(new PrivateTask(), 0, 50);
	}
	public void stop() {
		timer.cancel();
	}

	private class PrivateTask extends TimerTask {
		@Override
		public void run() {
			control.tick();
		}
	}
}
