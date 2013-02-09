package jp.gr.java_conf.t_era.pom.common.overlay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingOverlay implements OverlayDrawing {
	private static final String[] VIEW = {"■", "■■", "■■■"};
	private static final Color MASK = new Color(16,64,16,96);

	private final OverlayPanel parent;

	private Timer timer;
	private int count;
	private boolean isWaiting;

	public WaitingOverlay(OverlayPanel parent) {
		this.parent = parent;
	}

	public void StartMotion() {
		isWaiting = true;
		timer = new Timer();
		timer.schedule(new AnimationTask(), 0, 300);

		setViews();
	}
	private void setViews()
	{
		for (Component c : parent.getComponents()) {
			c.setEnabled(!isWaiting);
		}
		parent.repaint();
	}
	public void StopMotion() {
		isWaiting = false;
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		setViews();
	}

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		if (isWaiting) {
			int width = size.width;
			int height = size.height;

			g.setColor(MASK);
			g.fillRect(0,0,width,height);
			g.setColor(Color.GREEN);
			Font def = g.getFont();
			Font font = new Font(def.getName(), Font.BOLD ,def.getSize() + 2);
			g.setFont(font);
			String value = VIEW[count];
			Rectangle2D rect = font.getStringBounds(value, g.getFontMetrics().getFontRenderContext());
			g.drawString(value, width/2 - (int)(rect.getWidth()/2), height/2 + (int)(rect.getHeight()/4));
		}
	}


	private class AnimationTask extends TimerTask {
		@Override
		public void run() {
			count = (count + 1) % VIEW.length;
			if (parent != null) {
				parent.repaint();
			}
		}
	}
}
