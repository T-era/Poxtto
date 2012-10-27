package ytel.pom.gui.main;

import java.awt.Dimension;
import java.awt.Graphics;

import ytel.pom.control.ModeManager;
import ytel.pom.gui.parts.OverlayDrawing;

public class MainPanelOverlay implements OverlayDrawing {
	public final ModeManager mode;
	private final OverlayDrawing startView;
	private final OverlayDrawing doneView;
	private final OverlayDrawing shiftView;
	private boolean shiftPushed;

	public MainPanelOverlay(ModeManager mode) {
		this.mode = mode;
		startView  = new StartView();
		doneView = null;
		shiftView = null;
	}
	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		switch (mode.getMode()) {
		case Init:
		case Wait:
			startView.paintOverlay(g, size);
			return;
		case Going:
			if (shiftPushed)
				shiftView.paintOverlay(g, size);
			return;
		case Done:
			doneView.paintOverlay(g, size);
		}
	}

	public void shift(boolean pushed){
		shiftPushed = pushed;
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
