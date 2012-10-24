package ytel.pom.gui;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ytel.pom.gui.parts.OverlayPanel;

public class MainForm {
	public MainForm() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = frame.getContentPane();
		JTabbedPane tab = new JTabbedPane();
		tab.addTab("1", new JLabel("test1"));
		tab.addTab("2", new JLabel("test2"));
		JPanel foreground = new JPanel();
		foreground.add(new JButton());
		foreground.setBackground(Color.GREEN);
		tab.addTab("3", new OverlayPanel(foreground, new WaitingPanel()));
		tab.setEnabledAt(1, false);
		con.add(tab);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new MainForm();
	}
}
