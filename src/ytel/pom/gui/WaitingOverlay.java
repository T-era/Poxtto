package ytel.pom.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

import ytel.pom.gui.parts.OverlayDrawing;
import ytel.pom.gui.parts.OverlayPanel;

public class WaitingOverlay implements OverlayDrawing {
	private static final String[] VIEW = {"��", "����", "������"};
	private static Color MASK_COLOR = new Color(0,64,0,96);
	private static Color VIEW_COLOR = new Color(0,128,0,255);

	private final OverlayPanel parent;
	private Timer timer;
	private boolean isWaiting = false;
	private int count;
	
	public WaitingOverlay(OverlayPanel parent) {
		this.parent = parent;
	}
	
	public void startWaiting() {
		if (isWaiting) throw new IllegalStateException("Already started.");
		isWaiting = true;
		timer = new Timer();
		timer.schedule(new MyTimerTask(), 0, 300);

		for (Component child : parent.getComponents()) {
			child.setEnabled(false);
		}
		parent.repaint();
}
	public void stopWaiting() {
		if (! isWaiting)throw new IllegalStateException("Not started.");
		isWaiting = false;
		timer.cancel();
		timer = null;

		for (Component child : parent.getComponents()) {
			child.setEnabled(true);
		}
		parent.repaint();
	}

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		if (isWaiting) {
			int width = size.width;
			int height = size.height;
			Font defaultFont = g.getFont();
			Font viewFont = new Font(defaultFont.getName(), Font.BOLD, defaultFont.getSize() + 2);
			g.setFont(viewFont);
			g.setColor(MASK_COLOR);
			g.fillRect(0, 0, width, height);
			g.setColor(VIEW_COLOR);
			String str = VIEW[count];
			Rectangle2D rect = viewFont.getStringBounds(str, ((Graphics2D)g).getFontRenderContext());
			g.drawString(VIEW[count], width/2 - (int)(rect.getWidth()/2), height/2 + (int)(rect.getHeight()/4));
		}
	}

	private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			count = (count + 1) % VIEW.length;
			parent.repaint();
		}
	}
}
