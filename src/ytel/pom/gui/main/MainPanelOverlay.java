package ytel.pom.gui.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ytel.pom.gui.parts.OverlayDrawing;

public class MainPanelOverlay implements OverlayDrawing {
	public final ModeManager mode;
	private final OverlayDrawing startView;
	private final OverlayDrawing doneView;
	private final OverlayDrawing shiftView;
	private boolean shiftPushed;

	public MainPanelOverlay() {
		mode = new ModeManager();
		startView  = new StartView();
		doneView = null;
		shiftView = null;
	}
	@Override
	public void paintOverlay(Graphics g, Dimension size) {
		switch (mode.mode) {
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

	private class ModeManager extends MouseAdapter {
		private Mode mode = Mode.Init;
		private boolean budyReady = false;

		void start() {
			synchronized(this) {
				if (budyReady) {
					mode = Mode.Going;
				} else {
					mode = Mode.Wait;
				}
			}
		}
		void budyReady() {
			synchronized (this) {
				budyReady = true;
				if (mode == Mode.Wait)
					mode = Mode.Going;
			}
		}
		void done() {
			mode = Mode.Done;
		}
		void reset() {
			mode = Mode.Init;
			budyReady = false;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (mode == Mode.Init) {
					start();
				}
			}
		}
	}

	public static enum Mode {
		Init, Wait, Going, Done
	}
}
