package ytel.pom.gui.main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.JButton;

import ytel.pom.gui.parts.OverlayDrawing;

public class StartView implements OverlayDrawing {
	private final JButton buttonStart;
	private final Dimension buttonSize = new Dimension(70, 25);

	public StartView(Action startAction) {
		buttonStart = new JButton(startAction);
		buttonStart.setSize(buttonSize);
	}

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		int x = size.width / 2 - buttonSize.width / 2;
		int y = size.height / 2 - buttonSize.height / 2;;

		buttonStart.setLocation(x, y);
		buttonStart.paint(g);
	}
}
