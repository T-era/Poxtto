package ytel.pom.control;

import java.net.InetAddress;

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
	public void shakeHandCompleteAction(InetAddress host) {
		shakeHandDone(host);
	}

	@Override
	public void responseRecievedAction(InetAddress host) {
		shakeHandDone(host);
	}

	private void shakeHandDone(InetAddress host) {
		mainForm.shakeHandComplete();
		mainForm.setGameMode(host);
	}

	@Override
	public void pingTimeoutAction() {
		mainForm.pingTimeout();
	}

public static void main(String[] args) {
	new MenuControlImpl().start();
}
}
