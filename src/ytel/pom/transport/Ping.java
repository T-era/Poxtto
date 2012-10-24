package ytel.pom.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Ping implements Runnable {
	public final String host;
	public final int port;
	public boolean stopped;

	public Ping(String host) {
		this.host = host;
		this.port = Port.PING_PORT;

		stopped = false;
		new Thread(this).start();
	}

	public boolean put() {
		try {
			Socket pingSocket = new Socket(host, port);
			OutputStream stream = pingSocket.getOutputStream();
			pingSocket.setSoTimeout(Port.PING_SO_TIMEOUT);
			stream.write(Port.PING_MESSAGE);
			stream.flush();

			InputStream input = pingSocket.getInputStream();
			int ret = input.read();
			pingSocket.close();
			return ret == Port.PING_MESSAGE;
		} catch (IOException ex) {
			return false;
		}
	}

	public void run() {
		try {
			ServerSocket listener = new ServerSocket(port);
			try {
				do {
					Socket socket = listener.accept();
					try {
					InputStream input = socket.getInputStream();
					if (input.read() == Port.PING_MESSAGE) {
						OutputStream output = socket.getOutputStream();
						output.write(Port.PING_MESSAGE);
						output.flush();
					}
					} finally {
						socket.close();
					}
				} while(!stopped);
			} finally {
				stopped = true;
				listener.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
