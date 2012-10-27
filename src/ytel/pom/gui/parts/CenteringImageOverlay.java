package ytel.pom.gui.parts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class CenteringImageOverlay implements OverlayDrawing {
	private final BufferedImage image;
	private final Dimension imageSize;

	public CenteringImageOverlay(String path, Dimension imageSize, Component parent) throws IOException, InterruptedException {
		this.imageSize = imageSize;
		InputStream input = this.getClass().getResourceAsStream(path);
		try {
			image = ImageIO.read(input);
		} finally {
			input.close();
		}
	}

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		int x = size.width / 2 - imageSize.width / 2;
		int y = size.height / 2 - imageSize.height / 2;

		g.setColor(Color.WHITE);
		g.fillRect(0,0,size.width, size.height);
		g.drawImage(image, x, y, imageSize.width, imageSize.height, null);
	}
}
