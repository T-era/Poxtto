package jp.gr.java_conf.t_era.pom.main.overlay;

import java.awt.Dimension;
import java.awt.Graphics;

import jp.gr.java_conf.t_era.pom.ApplicationContext;
import  jp.gr.java_conf.t_era.pom.common.overlay.OverlayDrawing;
import jp.gr.java_conf.t_era.pom.main.KeyDelegate;

public class MainPanelOverlay implements OverlayDrawing {
	private final OverlayDrawing startView;
	private MyInnerOverlay innerOverlay = null;

	public MainPanelOverlay() {
		startView = new StartView();
	}
	public void invalidate(ApplicationContext context, KeyDelegate key) {
		innerOverlay = new MyInnerOverlay(context, key);
	}

	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		if (innerOverlay == null) {
			startView.paintOverlay(g, size);
		} else {
			innerOverlay.paintOverlay(g, size);
		}
	}

	private class MyInnerOverlay implements OverlayDrawing {
		private final ApplicationContext context;
		private final KeyDelegate key;
		private final OverlayDrawing doneView;
		private final OverlayDrawing shiftView;

		private MyInnerOverlay(ApplicationContext context, KeyDelegate key) {
			this.context = context;
			this.key = key;
			doneView = new GameOverView();
			shiftView = new SpecialModeOverlay();
		}

		@Override
		public void paintOverlay(Graphics g, Dimension size) {
			switch (context.getMode()) {
			case Preparing:
				startView.paintOverlay(g, size);
				return;
			case Running:
				if (key.isShiftPushed())
					shiftView.paintOverlay(g, size);
				return;
			case Done:
				doneView.paintOverlay(g, size);
				return;
			default:
				throw new RuntimeException("??");
			}
		}
	}
}
