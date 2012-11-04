package ytel.pom.gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import ytel.pom.gui.parts.OverlayDrawing;

public class GameOverView implements OverlayDrawing {
	private static final Color MASK = new Color(16,64,16,96);

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		g.setColor(MASK);
		g.fillRect(0, 0, size.width, size.height);
		
	}
}
