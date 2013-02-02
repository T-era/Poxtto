package ytel.pom.connect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ytel.pom.ApplicationContext;
import  ytel.pom.common.overlay.OverlayPanel;
import  ytel.pom.common.overlay.WaitingOverlay;
import ytel.pom.common.transport.send.PingResponseListener;
import ytel.pom.common.transport.send.PrepareSend;
import ytel.view.BorderLayoutBuilder;

public class ConnectPanel {
	public final OverlayPanel  panel;
	private final JTextField inputIpHost;
	private final WaitingOverlay waitingOverlay;
	private final JButton connectButton;

	public ConnectPanel() {
		panel = new OverlayPanel();

		waitingOverlay= new WaitingOverlay(panel);
		panel.setOverlayDrawing(waitingOverlay);

		inputIpHost = new JTextField("localhost");
		connectButton = new JButton("Connect");

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

	public void invalidate(final ApplicationContext context) {
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setWaitingMode(true);
				try {
					PrepareSend.putPing(InetAddress.getByName(inputIpHost.getText())
							, new PingResponseListener() {
								@Override
								public void responseRecievedAction(InetAddress hostName) {
									setWaitingMode(false);
									context.shakeHandComplete(hostName);
								}

								@Override
								public void pingTimeoutAction() {
									setWaitingMode(false);
								}
					});
				} catch (UnknownHostException e1) {
					JOptionPane.showMessageDialog(panel, "Error! \nUnknown host.");
					setWaitingMode(false);

					e1.printStackTrace();
				}
			}
		});
	}
}
