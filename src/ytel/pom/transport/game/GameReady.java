package ytel.pom.transport.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ytel.pom.transport.Ping;
import ytel.pom.transport.TransportConsts;

public class GameReady implements Runnable {
	public final int port;
	private final String budyHost;
	private boolean stopped;
	private final GameReadyRecieveListener listener;

	public GameReady(GameReadyRecieveListener listener, String budyHost) {
		this.port = TransportConsts.START_PORT;
		this.budyHost = budyHost;
		this.listener = listener;

		stopped = false;
		new Thread(this).start();
	}

	/**
	 * START発信
	 * @param host
	 */
	public void put() {
		new Thread(new Ping(budyHost, port, TransportConsts.START_SO_TIMEOUT, TransportConsts.START_MESSAGE, TransportConsts.START_RESPONSE, listener)).start();
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			try {
				do {
					Socket socket = server.accept();
					try {
						if (socket.getInetAddress().equals(InetAddress.getByName(budyHost))) {
							InputStream input = socket.getInputStream();
							int x = input.read();
							if (x == TransportConsts.START_MESSAGE) {
								OutputStream output = socket.getOutputStream();
	
								output.write(TransportConsts.START_RESPONSE);
								output.flush();
								String host = socket.getInetAddress().getHostName();
								listener.BudyReadyRecieveAction(host);
							}
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
	public void stopServer() {
		stopped = true;
	}
}
