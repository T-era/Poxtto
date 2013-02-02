package ytel.pom.common.timer;

import java.util.Timer;
import java.util.TimerTask;

public class SystemTimer {
	private final TimerListener listener;
	private final Timer timer;

	private int fallingCountUp;
	private int fallingSpeed = 1;

	public SystemTimer(TimerListener listener) {
		this.listener = listener;
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
			synchronized(this) {
				fallingCountUp ++;
				if (fallingCountUp > fallingSpeed) {
					fallingCountUp = 0;
					listener.tickWithFall();
				} else {
					listener.tick();
				}
			}
		}
	}
}
