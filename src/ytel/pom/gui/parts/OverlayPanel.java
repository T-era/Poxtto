package ytel.pom.gui.parts;

import java.awt.Graphics;

import javax.swing.JPanel;

public class OverlayPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -350633222943564131L;

	private OverlayDrawing drawing;

	public void setOverlayDrawing(OverlayDrawing drawing) {
		this.drawing = drawing;
	}

	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		drawing.paintOverlay(g, getSize());
	}
}
