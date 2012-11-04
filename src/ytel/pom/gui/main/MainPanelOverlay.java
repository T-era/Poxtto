package ytel.pom.gui.main;

import java.awt.Dimension;
import java.awt.Graphics;

import ytel.pom.control.KeyDelegate;
import ytel.pom.control.ModeManager;
import ytel.pom.gui.parts.OverlayDrawing;

public class MainPanelOverlay implements OverlayDrawing {
	public final ModeManager mode;
	private final KeyDelegate key;
	private final OverlayDrawing startView;
	private final OverlayDrawing doneView;
	private final OverlayDrawing shiftView;

	public MainPanelOverlay(ModeManager mode, KeyDelegate key) {
		this.mode = mode;
		this.key = key;
		startView  = new StartView();
		doneView = new GameOverView();
		shiftView = new SpecialModeOverlay();
	}
	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		switch (mode.getMode()) {
		case Init:
		case Wait:
			startView.paintOverlay(g, size);
			return;
		case Going:
			if (key.isShiftPushed())
				shiftView.paintOverlay(g, size);
			return;
		case Done:
			doneView.paintOverlay(g, size);
		}
	}

	public void startClick() {
		mode.start();
	}
	public void catchReady() {
		mode.budyReady();
	}

	void done() {
		mode.done();
	}
	void reset() {
		mode.reset();
	}
}
