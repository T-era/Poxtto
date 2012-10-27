package ytel.pom.gui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import ytel.pom.control.MainControl;
import ytel.pom.control.MainControlImpl;
import ytel.pom.gui.connect.ConnectPanel;
import ytel.pom.gui.main.MainPanel;
import ytel.pom.transport.ShakeHandCompleteListener;

public class MainForm {
	private static final int POM_SIZE = 20;
	private final JTabbedPane tab = new JTabbedPane();
	private ConnectPanel connectPanel;
	private MainPanel mainPanel;
	private MainControl mainControl;
	public MainForm() {
//		Init();
		//test2();
	}
	public void init(ShakeHandCompleteListener listener) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = frame.getContentPane();

		connectPanel = new ConnectPanel(listener);
		mainControl = new MainControlImpl(POM_SIZE);
		mainPanel = new MainPanel(mainControl);
		tab.addTab("接続", connectPanel.panel);
		tab.addTab("test", mainPanel.panel);

		con.add(tab);

		frame.pack();
		frame.setVisible(true);
	}

	public void SetConnectMode() {
		tab.setEnabledAt(0, true);
		tab.setEnabledAt(1, false);
		tab.setSelectedIndex(0);
	}
	public void SetGameMode() {
		tab.setEnabledAt(0, false);
		tab.setEnabledAt(1, true);
		tab.setSelectedIndex(1);
	}

	public void ShakeHandComplete() {
		connectPanel.SetWaitingMode(false);
	}
	public void PingTimeout() {
		connectPanel.SetWaitingMode(false);
	}
}
