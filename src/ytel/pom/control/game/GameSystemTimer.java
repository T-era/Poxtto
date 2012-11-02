package ytel.pom.control.game;

import java.util.Timer;
import java.util.TimerTask;

import ytel.pom.control.MainControl;

public class GameSystemTimer {
	private final MainControl control;

	public GameSystemTimer(MainControl control) {
		this.control = control;
	}
	public void start() {
		new Timer().scheduleAtFixedRate(new PrivateTask(), 0, 5);
	}

	private class PrivateTask extends TimerTask {
		@Override
		public void run() {
			control.tick();
		}
	}
}
