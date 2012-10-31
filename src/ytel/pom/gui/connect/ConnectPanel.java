package ytel.pom.gui.connect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

		inputIpHost = new JTextField("localhost");
		JButton connectButton = new JButton("Connect");

		final ShakeHand sh = new ShakeHand(control);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setWaitingMode(true);
				try {
					sh.put(InetAddress.getByName(inputIpHost.getText()));
				} catch (UnknownHostException e1) {
					// TODO
					e1.printStackTrace();
				}
			}
		});

		BorderLayoutBuilder builder = new BorderLayoutBuilder();
		builder.add(new JLabel("Connect to..."), BorderLayoutBuilder.Direction.North, BorderLayoutBuilder.Direction.West);
		builder.add(inputIpHost, BorderLayoutBuilder.Direction.Center, BorderLayoutBuilder.Direction.North);
		builder.add(connectButton, BorderLayoutBuilder.Direction.South, BorderLayoutBuilder.Direction.East);
		builder.layoutComponent(panel);
	}

	public void setWaitingMode(boolean waiting) {
		if (waiting) {
			waitingOverlay.StartMotion();
		} else {
			waitingOverlay.StopMotion();
		}
	}
}
