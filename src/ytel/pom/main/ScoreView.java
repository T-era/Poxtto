package ytel.pom.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

import ytel.pom.main.model.damage.Damage;

public final class ScoreView {
	private static final Font FONT_RANK = new Font(Font.MONOSPACED, Font.BOLD, 18);
	private static final Font FONT_CENT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	private static final Dimension PREFERRED = new Dimension(30, 20);

	public final JComponent panel;
	private int score;

	public ScoreView() {
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				int rank = score / 100;
				int cent = score % 100;

				g.setFont(FONT_RANK);
				g.drawString(rankToString(rank), 0, 18);
				g.setFont(FONT_CENT);
				g.drawString(centToString(cent), 10, 18);
			}
		};
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		panel.setPreferredSize(PREFERRED);
	}
	public int flushDamage() {
		return flush();
	}
	public Damage flushToDamage() {
		return new Damage(flush());
	}
	private int flush() {
		int temp = score;
		score = 0;
		return temp;
	}
	public void clearScore() {
		score = 0;
	}
	public void addScore(int d) {
		score += d;
	}

	private String rankToString(int rank) {
		return Integer.toString(rank);
	}
	private String centToString(int cent) {
		return String.format("%1$02d", cent);
	}
}
