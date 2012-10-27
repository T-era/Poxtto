package ytel.pom.gui.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import ytel.pom.control.KeyDelegate;
import ytel.pom.control.MainControl;
import ytel.pom.gui.parts.OverlayPanel;

public class MainPanel {
	public final OverlayPanel panel;
	public final MainPanelOverlay overlay;

	public MainPanel(final MainControl control) {
		panel = new OverlayPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3666870835986288160L;

			@Override
			protected void paintComponent(java.awt.Graphics g) {
				control.drawPoms(g);
			}
		};
		overlay = new MainPanelOverlay();
		panel.setOverlayDrawing(overlay);
		panel.setFocusable(true);
		KeyDelegate id = new KeyDelegate(control);
		panel.addKeyListener(id);
		panel.addMouseListener(overlay.mode);

		int width = MainControl.POM_SIZE_W * MainControl.WIDTH;
		int height = MainControl.POM_SIZE_H * MainControl.HEIGHT;
		Dimension dim = new Dimension(width, height);
		panel.setSize(dim);
		panel.setPreferredSize(dim);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		panel.setBorder(new LineBorder(Color.BLUE, 5));
	}
}
