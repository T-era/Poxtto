package ytel.pom.gui.connect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ytel.pom.gui.parts.OverlayPanel;
import ytel.pom.gui.parts.WaitingOverlay;
import ytel.pom.transport.menu.ShakeHand;
import ytel.pom.transport.menu.ShakeHandCompleteListener;
import ytel.view.BorderLayoutBuilder;

public class ConnectPanel {
	public final OverlayPanel  panel;
	private final JTextField inputIpHost;
	private final WaitingOverlay waitingOverlay;

	public ConnectPanel(ShakeHandCompleteListener control) {
		panel = new OverlayPanel();

		waitingOverlay= new WaitingOverlay(panel);
		panel.setOverlayDrawing(waitingOverlay);

		inputIpHost = new JTextField();
		JButton connectButton = new JButton("Connect");

		final ShakeHand sh = new ShakeHand(control);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SetWaitingMode(true);
				sh.put(inputIpHost.getText());
			}
		});

		BorderLayoutBuilder builder = new BorderLayoutBuilder();
		builder.add(new JLabel("Connect to..."), BorderLayoutBuilder.Direction.North, BorderLayoutBuilder.Direction.West);
		builder.add(inputIpHost, BorderLayoutBuilder.Direction.Center, BorderLayoutBuilder.Direction.North);
		builder.add(connectButton, BorderLayoutBuilder.Direction.South, BorderLayoutBuilder.Direction.East);
		builder.layoutComponent(panel);
	}

//	@Override
//	public void ShakeHandCompleteAction(String hostName) {
//		SetWaitingMode(false);
//	}

	public void SetWaitingMode(boolean waiting) {
		if (waiting) {
			waitingOverlay.StartMotion();
		} else {
			waitingOverlay.StopMotion();
		}
	}
}
