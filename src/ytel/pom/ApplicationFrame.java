package ytel.pom;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ytel.pom.connect.ConnectPanel;
import ytel.pom.main.GameControl;
import ytel.pom.main.KeyDelegate;
import ytel.pom.main.MainPanel;
import ytel.pom.main.model.pom.NextPomGenerator;
import  ytel.pom.prepare.PrepareContext;

public class ApplicationFrame {
	private final JTabbedPane tab = new JTabbedPane();
	private final ConnectPanel connectPanel;
	public final MainPanel mainPanel;
	public final NextPomGenerator nextGenerator;

	public ApplicationFrame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = frame.getContentPane();

		nextGenerator = new NextPomGenerator();

		connectPanel = new ConnectPanel();
		tab.addTab("接続", connectPanel.panel);
		mainPanel = new MainPanel(nextGenerator);
		tab.addTab("ゲーム", mainPanel.panel);

		con.add(tab);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void setConnectMode(ApplicationContext context) {
		tab.setEnabledAt(0, true);
		tab.setEnabledAt(1, false);
		tab.setSelectedIndex(0);
		connectPanel.invalidate(context);
	}

	public void setPrepareeMode(ApplicationContext context, final PrepareContext pContext, KeyDelegate key) {
		tab.setEnabledAt(0, false);
		tab.setEnabledAt(1, true);
		tab.setSelectedIndex(1);
		mainPanel.invalidate(context, pContext, key);
	}

	public void setGameMode(GameControl control) {
		mainPanel.setRunning(control);
		mainPanel.repaint();
	}

	public void repaint() {
		mainPanel.repaint();
	}
}