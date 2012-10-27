package ytel.pom.gui.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import ytel.pom.control.KeyDelegate;
import ytel.pom.control.MainControl;
import ytel.pom.control.ModeChangedListener;
import ytel.pom.control.ModeManager;
import ytel.pom.control.ModeManager.Mode;
import ytel.pom.gui.parts.OverlayPanel;

public class MainPanel {
	public final OverlayPanel panel;
	public final MainPanelOverlay overlay;
	private final ModeManager modeManager;

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
		this.modeManager = new ModeManager(new ModeChangedListener() {
			@Override
			public void modeChanged(Mode newMode) {
				panel.repaint();
			}
		});
		overlay = new MainPanelOverlay(modeManager);
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

	public void init(String budyHost) {
		modeManager.init(budyHost);
	}
}
