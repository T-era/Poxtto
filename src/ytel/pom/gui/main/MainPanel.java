package ytel.pom.gui.main;

import ytel.pom.control.KeyDelegate;
import ytel.pom.control.MainControl;
import ytel.pom.gui.parts.OverlayPanel;

public class MainPanel {
	public final OverlayPanel panel;

	public MainPanel(final MainControl control) {
		panel = new OverlayPanel() {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				control.drawPoms(g);
			}
		};
		panel.setOverlayDrawing(new MainPanelOverlay());
		panel.setFocusable(true);
		panel.addKeyListener(new KeyDelegate(control));
	}
}
