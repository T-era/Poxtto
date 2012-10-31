package ytel.pom.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class RecieveService implements Runnable {
	/**
	 * SINGLETON
	 */
	public static final RecieveService service = new RecieveService();

	private final ServerSocket server;
	private volatile ServiceMode mode = ServiceMode.NoneMode;
	private InetAddress budyHost;

	public RecieveService() {
		try {
			server = new ServerSocket(TransportConsts.SERVER_PORT);
			new Thread(this).start();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void setMode(ServiceMode newMode) {
		this.mode = newMode;
	}
	public void setBudy(InetAddress budyHost) {
		this.budyHost = budyHost;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Socket socket = server.accept();
				ServiceMode temp = this.mode;
				try {
					if (! temp.budyLocked()
							|| socket.getInetAddress().equals(budyHost)) {
						temp.messageRecievedAction(socket, budyHost);
					}
				} finally {
					socket.close();
				}
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
