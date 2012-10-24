package ytel.pom.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class WaitingPanel extends JPanel {
	private MyTimerTask timerTask = new MyTimerTask();
	private static final String[] VIEW = {".", "..", "..."};

	public WaitingPanel() {
		new Timer().schedule(timerTask, 1000, 300);
		this.setBackground(new Color(0, 0, 0, 128));
	}

	@Override
	public void paint(Graphics g) {
		timerTask.Paint(g);
	}

	private class MyTimerTask extends TimerTask {
		private int count;
		@Override
		public void run() {
			count = (count + 1) % VIEW.length;
			getParent().repaint();
		}

		private void Paint(Graphics g) {
			Dimension size = getSize();
			int width = size.width;
			int height = size.height;
			g.drawString(VIEW[count], width/2, height/2);
		}
	}
}
