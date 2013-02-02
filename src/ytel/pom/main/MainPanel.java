package ytel.pom.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ytel.pom.ApplicationContext;
import  ytel.pom.common.overlay.OverlayPanel;
import ytel.pom.main.model.pom.NextPomGenerator;
import ytel.pom.main.overlay.MainPanelOverlay;
import  ytel.pom.prepare.PrepareContext;
import ytel.view.BorderLayoutBuilder;
import ytel.view.BorderLayoutBuilder.Direction;

public class MainPanel {
	private static final Color COLOR1 = new Color(60,30,30,255);
	private static final Color COLOR2 = new Color(80,40,40,255);

	public final JComponent panel;
	public final ScoreView scoreView;
	private final MainPanelOverlay overlay;
	private final OverlayPanel panelIn;

	private GameControl control;

	public MainPanel(NextPomGenerator nextGenerator) {
		this.panel = new JPanel();
		this.scoreView = new ScoreView();
		this.overlay = new MainPanelOverlay();
		this.panelIn = new OverlayPanel() {
			/**
			 *
			 */
			private static final long serialVersionUID = 3666870835986288160L;

			@Override
			protected void paintComponent(Graphics g) {
				for (int x = 0; x < MainContext.WIDTH; x ++) {
					for (int y = 0; y < MainContext.HEIGHT; y ++) {
						if ((x + y) % 2 == 0) {
							g.setColor(COLOR1);
						} else {
							g.setColor(COLOR2);
						}
						g.fillRect(x * MainContext.POM_SIZE_W, y * MainContext.POM_SIZE_H, MainContext.POM_SIZE_W, MainContext.POM_SIZE_H);
					}
				}

				if (control != null) control.drawAll(g);
			}
		};
		panelIn.setOverlayDrawing(overlay);
		panelIn.setFocusable(true);

		final JPanel mainView = new JPanel(new BorderLayout());
		mainView.setBorder(new LineBorder(Color.BLUE, 3));

		int width = MainContext.POM_SIZE_W * MainContext.WIDTH;
		int height = MainContext.POM_SIZE_H * MainContext.HEIGHT;
		Dimension dim = new Dimension(width, height);
		panelIn.setSize(dim);
		panelIn.setPreferredSize(dim);
		panelIn.setMaximumSize(dim);
		panelIn.setMinimumSize(dim);
		mainView.add(panelIn);

		BorderLayoutBuilder blb = new BorderLayoutBuilder();
		blb.add(mainView, Direction.Center);
		blb.add(scoreView.panel, Direction.West, Direction.North);
		blb.add(nextGenerator.panel, Direction.West, Direction.Center);
		blb.layoutComponent(panel);
	}

	public void invalidate(ApplicationContext context, PrepareContext pContext, KeyDelegate key) {
		overlay.invalidate(context, key);
		panelIn.addKeyListener(key);
		panelIn.addMouseListener(pContext.toPrepareMouseListener());
	}
	public void setRunning(GameControl control) {
		this.control = control;
	}

	public void repaint() {
		panel.repaint();
	}
}
