package ytel.pom.transport.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ytel.pom.transport.Ping;
import ytel.pom.transport.RecieveService;
import ytel.pom.transport.ServiceMode;
import ytel.pom.transport.TransportConsts;

public class GameReady {
	private final InetAddress budyHost;
	private final GameReadyRecieveListener listener;

	public GameReady(final GameReadyRecieveListener listener, final InetAddress budyHost) {
		this.budyHost = budyHost;
		this.listener = listener;
		RecieveService.service.setBudy(budyHost);
		RecieveService.service.setMode(new ServiceMode() {
			@Override
			public boolean budyLocked() {
				return true;
			}
			@Override
			public void messageRecievedAction(Socket socket, InetAddress budyHost) throws IOException {
				InputStream input = socket.getInputStream();
				int x = input.read();
				if (x == TransportConsts.START_MESSAGE) {
					OutputStream output = socket.getOutputStream();

					output.write(TransportConsts.START_RESPONSE);
					output.flush();
					listener.BudyReadyRecieveAction(budyHost);
				}
			}
		});
	}

	/**
	 * START発信
	 * @param host
	 */
	public void put() {
		new Thread(new Ping(budyHost, TransportConsts.SERVER_PORT, TransportConsts.START_SO_TIMEOUT, TransportConsts.START_MESSAGE, TransportConsts.START_RESPONSE, listener)).start();
	}
}
