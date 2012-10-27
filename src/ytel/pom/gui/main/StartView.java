package ytel.pom.gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ytel.pom.gui.parts.OverlayDrawing;

public class StartView implements OverlayDrawing {
	private static final String START_MESSAGE = "Click anywhere to start!";
	private static final Color MASK = new Color(128,0,0,64);

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		g.setColor(MASK);
		g.fillRect(0,0, size.width, size.height);
		g.setColor(Color.BLACK);
		Font f = g.getFont();
		Rectangle2D rect = f.getStringBounds(START_MESSAGE, ((Graphics2D)g).getFontRenderContext());
		int x = size.width / 2 - (int)rect.getWidth()/ 2;
		int y = size.height / 2 + (int)rect.getHeight()/4;

		g.drawString(START_MESSAGE, x, y);
	}
}
