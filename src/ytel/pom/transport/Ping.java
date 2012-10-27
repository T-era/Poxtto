package ytel.pom.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Ping implements Runnable {
	public final String host;
	public final int port;
	private final ShakeHandCompleteListener listener;

	public Ping(String host, int port, ShakeHandCompleteListener listener) {
		this.host = host;
		this.port = port;
		this.listener = listener;
	}

	public void run() {
		try {
			Socket pingSocket = new Socket(host, port);
			OutputStream stream = pingSocket.getOutputStream();
			pingSocket.setSoTimeout(Port.PING_SO_TIMEOUT);
			stream.write(Port.PING_MESSAGE);
			stream.flush();

			InputStream input = pingSocket.getInputStream();
			int ret = input.read();
			if (ret == Port.PING_MESSAGE) {
				listener.ShakeHandCompleteAction(host);
			}
			pingSocket.close();
		} catch (IOException ex) {
			listener.PingTimeoutAction();
		}
	}
}
