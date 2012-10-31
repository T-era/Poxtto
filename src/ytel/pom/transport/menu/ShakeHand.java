package ytel.pom.transport.menu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ytel.pom.transport.Ping;
import ytel.pom.transport.RecieveService;
import ytel.pom.transport.ServiceMode;
import ytel.pom.transport.TransportConsts;

/**
 * 
 * @author y-tel
 *
 * TODO PING 発信後、レスポンス受信前に、別のPINGを受信した場合、両方に良い返事を返してしまうバグ。
 */
public class ShakeHand {
	private final ShakeHandCompleteListener listener;

	public ShakeHand(final ShakeHandCompleteListener listener) {
		this.listener = listener;
		RecieveService.service.setMode(new ServiceMode() {
			@Override
			public boolean budyLocked() {
				return false;
			}
			@Override
			public void messageRecievedAction(Socket socket, InetAddress host) throws IOException {
				try {
					InputStream input = socket.getInputStream();
					if (input.read() == TransportConsts.PING_MESSAGE) {
						OutputStream output = socket.getOutputStream();

						output.write(TransportConsts.PING_MESSAGE);
						output.flush();
						listener.shakeHandCompleteAction(socket.getInetAddress());
					}
				} finally {
					socket.close();
				}
			}
		});
	}

	/**
	 * PING 発信
	 * @param host
	 */
	public void put(InetAddress host) {
		new Thread(new Ping(host, TransportConsts.SERVER_PORT, TransportConsts.PING_MESSAGE, TransportConsts.PING_MESSAGE, TransportConsts.PING_SO_TIMEOUT, listener)).start();
	}
}
