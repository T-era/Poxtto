package  ytel.pom.common.transport;

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
	private volatile RecieveServiceMode mode = RecieveServiceMode.NoneMode;
	private InetAddress budyHost;

	private RecieveService() {
		try {
			server = new ServerSocket(TransportConsts.SERVER_PORT);
			new Thread(this, "Recieve").start();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void setMode(RecieveServiceMode newMode) {
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
				try {
					this.mode.messageRecievedAction(socket, budyHost);
				} finally {
					socket.close();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}

			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
