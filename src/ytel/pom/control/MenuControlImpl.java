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
		mainForm.SetConnectMode();
	}

	@Override
	public void ShakeHandCompleteAction(String hostName) {
		ShakeHandDone();
	}

	@Override
	public void ResponseRecievedAction(String hoseName) {
		ShakeHandDone();
	}

	private void ShakeHandDone() {
		mainForm.ShakeHandComplete();
		mainForm.SetGameMode();
	}

	@Override
	public void PingTimeoutAction() {
		mainForm.PingTimeout();
	}

public static void main(String[] args) {
	new MenuControlImpl().start();
}
}
