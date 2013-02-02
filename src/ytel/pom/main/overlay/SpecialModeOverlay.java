package ytel.pom.main.overlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import  ytel.pom.common.overlay.OverlayDrawing;

public class SpecialModeOverlay implements OverlayDrawing {
	private static final Color BACKGROUND = new Color(0, 64, 0, 32);
	private static final int ICON_SIZE = 32;
	private static final Image DEFENCE;
	private static final Image ATTACK;

	static {
		try {
			DEFENCE = ImageIO.read(ClassLoader.getSystemResourceAsStream("image\\Shield.gif"));
			ATTACK = ImageIO.read(ClassLoader.getSystemResourceAsStream("image\\Fire.gif"));
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		int centerX = size.width / 2;
		int centerY = size.height / 2;
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, size.width, size.height);

		g.drawImage(DEFENCE, centerX - ICON_SIZE * 3 / 2, centerY - ICON_SIZE / 2, null);
		g.drawImage(ATTACK, centerX + ICON_SIZE * 1 / 2, centerY - ICON_SIZE / 2, null);

		g.setColor(Color.WHITE);
		Font dflt = g.getFont();
		Font font = new Font(dflt.getName(), Font.BOLD, dflt.getSize());
		g.setFont(font);
		String defence = "Z";
		Rectangle2D rectD = font.getStringBounds(defence, g.getFontMetrics().getFontRenderContext());
		g.drawString(defence, centerX - ICON_SIZE - (int)rectD.getWidth() / 2, centerY + (int)rectD.getHeight() / 4);
		String attack = "X";
		Rectangle2D rectA = font.getStringBounds(attack, g.getFontMetrics().getFontRenderContext());
		g.drawString(attack, centerX + ICON_SIZE - (int)rectA.getWidth() / 2, centerY + (int)rectA.getHeight() / 4);
	}
}
