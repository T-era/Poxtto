package ytel.pom.gui.parts;

import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class OverlayPanel extends JPanel {
	public OverlayPanel(Container forward, Container background) {
		this.setLayout(new OverlayLayout(this));
		this.add(background);
		this.add(forward);
	}
}
