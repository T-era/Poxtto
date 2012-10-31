package ytel.pom.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Soketメッセージを発信する。
 * listenerを指定することで、Responseを検知できる。
 * 
 * @author Yoshi
 *
 */
public class Ping implements Runnable {
	public final InetAddress host;
	public final int port;
	private final int timeout;
	private final int message;
	private final int responseExpect;
	private final PingResponseListener listener;

	public Ping(InetAddress host, int port, int timeout, int message, int responseExpect, PingResponseListener listener) {
		this.host = host;
		this.port = port;
		this.message = message;
		this.responseExpect = responseExpect;
		this.timeout = timeout;
		this.listener = listener;
	}

	public void run() {
		try {
			Socket pingSocket = new Socket(host, port);
			OutputStream stream = pingSocket.getOutputStream();
			pingSocket.setSoTimeout(timeout);
			stream.write(message);
			stream.flush();

			InputStream input = pingSocket.getInputStream();
			int ret = input.read();
			if (ret == responseExpect) {
				listener.responseRecievedAction(host);
			}
			pingSocket.close();
		} catch (IOException ex) {
			listener.pingTimeoutAction();
		}
	}
}
