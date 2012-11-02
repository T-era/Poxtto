package ytel.pom.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;

import ytel.pom.transport.game.GameReady;
import ytel.pom.transport.game.GameReadyRecieveListener;

public class ModeManager extends MouseAdapter {
	private final ModeChangedListener listener;
	private boolean budyReady = false;
	private GameReady gameReady;
	private Mode mode = Mode.Init;
	
	public ModeManager(ModeChangedListener listener) {
		this.listener = listener;
	}
	public void init(InetAddress budyHost) {
		this.gameReady = new GameReady(new GameReadyRecieveListener() {
			@Override
			public void responseRecievedAction(InetAddress hoseName) {
			}
			@Override
			public void pingTimeoutAction() {
			}
			@Override
			public void BudyReadyRecieveAction(InetAddress hostName) {
				budyReady();
			}
		}, budyHost);
	}

	public void start() {
		gameReady.put();
		synchronized(this) {
			if (budyReady) {
				mode = Mode.Going;
				listener.started();
			} else {
				mode = Mode.Wait;
			}
		}
	}
	public void budyReady() {
		synchronized (this) {
			budyReady = true;
			if (mode == Mode.Wait) {
				mode = Mode.Going;
				listener.started();
			}
		}
	}
	public void done() {
		synchronized (this) {
			mode = Mode.Done;
		}
		listener.ended();
	}
	public void reset() {
		synchronized (this) {
			mode = Mode.Init;
			budyReady = false;
		}
		listener.gotReady();
	}
	public Mode getMode() {
		return mode;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (mode == Mode.Init) {
				start();
			}
		}
	}

	public static enum Mode {
		Init, Wait, Going, Done
	}
	public static interface ModeChangedListener {
		void gotReady();
		void started();
		void ended();
	}
}
