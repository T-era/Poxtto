package  ytel.pom.recievemode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ytel.pom.ApplicationContext;
import ytel.pom.common.transport.RecieveServiceMode;
import ytel.pom.common.transport.TransportConsts;

public class ShakingHandMode implements RecieveServiceMode {
	private final ApplicationContext context;

	public ShakingHandMode(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void messageRecievedAction(Socket socket, InetAddress host) throws IOException {
		try {
			InputStream input = socket.getInputStream();
			if (input.read() == TransportConsts.PING_MESSAGE) {
				OutputStream output = socket.getOutputStream();

				output.write(TransportConsts.PING_MESSAGE);
				output.flush();
				context.shakeHandComplete(socket.getInetAddress());
			}
		} finally {
			socket.close();
		}
	}

}
