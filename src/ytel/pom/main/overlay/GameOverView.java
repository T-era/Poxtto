package ytel.pom.main.overlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ytel.pom.common.overlay.OverlayDrawing;

public class GameOverView implements OverlayDrawing {
	private static final String RESTART_MESSAGE = "Click to restart.";
	private static final Color MASK = new Color(16,64,16,96);

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		g.setColor(MASK);
		g.fillRect(0, 0, size.width, size.height);
		g.setColor(Color.WHITE);
		Font f = g.getFont();
		Font font = new Font(f.getName(), Font.BOLD, f.getSize() + 4);
		g.setFont(font);
		Rectangle2D rect = font.getStringBounds(RESTART_MESSAGE, ((Graphics2D)g).getFontRenderContext());
		int x = size.width / 2 - (int)rect.getWidth()/ 2;
		int y = size.height / 2 + (int)rect.getHeight()/4;

		g.drawString(RESTART_MESSAGE, x, y);
	}
}
