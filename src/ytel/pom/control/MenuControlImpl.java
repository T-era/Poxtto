package ytel.pom.control;

import ytel.pom.gui.MainForm;

public class MenuControlImpl implements MenuControl {
	private final MainForm mainForm;

	public MenuControlImpl() {
		mainForm = new MainForm();
	}

	@Override
	public void start() {
		mainForm.init(this);
		mainForm.setConnectMode();
	}

	@Override
	public void shakeHandCompleteAction(String hostName) {
		shakeHandDone(hostName);
	}

	@Override
	public void responseRecievedAction(String hostName) {
		shakeHandDone(hostName);
	}

	private void shakeHandDone(String hostName) {
		mainForm.shakeHandComplete();
		mainForm.setGameMode(hostName);
	}

	@Override
	public void pingTimeoutAction() {
		mainForm.pingTimeout();
	}

public static void main(String[] args) {
	new MenuControlImpl().start();
}
}
