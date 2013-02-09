package jp.gr.java_conf.t_era.pom.common.overlay;

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
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.getSize().width, this.getSize().height);
		super.paint(g);
	}

	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		drawing.paintOverlay(g, getSize());
	}
}
