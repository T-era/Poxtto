package  jp.gr.java_conf.t_era.pom.common.transport.send;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import jp.gr.java_conf.t_era.pom.common.transport.TransportConsts;

/**
 * Soketメッセージを発信する。
 * listenerを指定することで、Responseを検知できる。
 *
 * @author Yoshi
 *
 */
class Ping implements Runnable {
	private final InetAddress host;
	private final int port;
	private final int timeout;
	private final int message;
	private final int responseExpect;
	private final PingResponseListener listener;

	static void sendIntMessage(InetAddress host, int timeout, int message, int responseExpect, PingResponseListener listener) {
		new Thread(new Ping(host, TransportConsts.SERVER_PORT, timeout, message, responseExpect, listener)
				, "Send int" + message).start();
	}
	static <TMessage> void sendObjMessage(InetAddress host, int timeout, TMessage message, int responseExpect, PingResponseListener listener) {
		new Thread(new GPing<TMessage>(host, TransportConsts.SERVER_PORT, timeout, message, responseExpect, listener)
				, "Send dmg").start();
	}

	private Ping(InetAddress host, int port, int timeout, int message, int responseExpect, PingResponseListener listener) {
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
	private static class GPing<TMessage> implements Runnable {
		private final InetAddress host;
		private final int port;
		private final int timeout;
		private final TMessage message;
		private final int responseExpect;
		private final PingResponseListener listener;

		private GPing(InetAddress host, int port, int timeout, TMessage message, int responseExpect, PingResponseListener listener) {
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
				ObjectOutputStream stream = new ObjectOutputStream(pingSocket.getOutputStream());
				pingSocket.setSoTimeout(timeout);
				stream.writeObject(message);
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
}
