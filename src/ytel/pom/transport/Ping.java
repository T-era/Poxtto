package ytel.pom.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Ping implements Runnable {
	public final String host;
	public final int port;
	private final int timeout;
	private final PingResponseListener listener;

	public Ping(String host, int port, int timeout, PingResponseListener listener) {
		this.host = host;
		this.port = port;
		this.timeout = timeout;
		this.listener = listener;
	}

	public void run() {
		try {
			Socket pingSocket = new Socket(host, port);
			OutputStream stream = pingSocket.getOutputStream();
			pingSocket.setSoTimeout(timeout);
			stream.write(TransportConsts.PING_MESSAGE);
			stream.flush();

			InputStream input = pingSocket.getInputStream();
			int ret = input.read();
			if (ret == TransportConsts.PING_MESSAGE) {
				listener.ResponseRecievedAction(host);
			}
			pingSocket.close();
		} catch (IOException ex) {
			listener.PingTimeoutAction();
		}
	}
}
