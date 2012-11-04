package ytel.pom.gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.net.InetAddress;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ytel.pom.control.KeyDelegate;
import ytel.pom.control.MainControl;
import ytel.pom.control.ModeManager;
import ytel.pom.gui.parts.OverlayPanel;

public class MainPanel {
	private static final Color COLOR1 = new Color(60,30,30,255);
	private static final Color COLOR2 = new Color(80,40,40,255);
	private final OverlayPanel panel;
	private final ModeManager modeManager;
	public final JComponent border;
	public final MainPanelOverlay overlay;

	public MainPanel(final MainControl control, final ModeManager modeManager) {
		border = new JPanel(new BorderLayout());
		border.setBorder(new LineBorder(Color.BLUE, 3));

		panel = new OverlayPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3666870835986288160L;

			@Override
			protected void paintComponent(Graphics g) {
				for (int x = 0; x < MainControl.WIDTH; x ++) {
					for (int y = 0; y < MainControl.HEIGHT; y ++) {
						if ((x + y) % 2 == 0) {
							g.setColor(COLOR1);
						} else {
							g.setColor(COLOR2);
						}
						g.fillRect(x * MainControl.POM_SIZE_W, y * MainControl.POM_SIZE_H, MainControl.POM_SIZE_W, MainControl.POM_SIZE_H);
					}
				}
				control.drawPoms(g);
			}
		};
		this.modeManager = modeManager;
		KeyDelegate kd = new KeyDelegate(control);
		overlay = new MainPanelOverlay(modeManager, kd);
		panel.setOverlayDrawing(overlay);
		panel.setFocusable(true);
		panel.addKeyListener(kd);
		panel.addMouseListener(overlay.mode);

		int width = MainControl.POM_SIZE_W * MainControl.WIDTH;
		int height = MainControl.POM_SIZE_H * MainControl.HEIGHT;
		Dimension dim = new Dimension(width, height);
		panel.setSize(dim);
		panel.setPreferredSize(dim);
		panel.setMaximumSize(dim);
		panel.setMinimumSize(dim);
		border.add(panel);
	}

	public void init(InetAddress budyHost) {
		modeManager.init(budyHost);
	}

	public void repaint() {
		panel.repaint();
	}
}
