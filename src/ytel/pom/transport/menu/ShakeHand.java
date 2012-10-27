package ytel.pom.transport.menu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ytel.pom.transport.Ping;
import ytel.pom.transport.TransportConsts;

/**
 *
 * @author 22677478
 *
 * TODO PING 発信後、レスポンス受信前に、別のPINGを受信した場合、両方に良い返事を返してしまうバグ。
 */
public class ShakeHand implements Runnable {
	public final int port;
	private boolean stopped;
	private final ShakeHandCompleteListener listener;

	public ShakeHand(ShakeHandCompleteListener listener) {
		this.port = TransportConsts.PING_PORT;
		this.listener = listener;

		stopped = false;
		new Thread(this).start();
	}

	public void put(String host) {
		new Thread(new Ping(host, port, TransportConsts.PING_SO_TIMEOUT, listener)).start();
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			try {
				do {
					Socket socket = server.accept();
					try {
					InputStream input = socket.getInputStream();
					if (input.read() == TransportConsts.PING_MESSAGE) {
						OutputStream output = socket.getOutputStream();

						output.write(TransportConsts.PING_MESSAGE);
						output.flush();
						listener.ShakeHandCompleteAction(socket.getInetAddress().getHostName());
					}
					} finally {
						socket.close();
					}
				} while(!stopped);
			} finally {
				stopped = true;
				server.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
