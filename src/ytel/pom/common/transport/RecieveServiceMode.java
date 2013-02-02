package  ytel.pom.common.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public interface RecieveServiceMode {
	void messageRecievedAction(Socket socket, InetAddress host) throws IOException;

	static final RecieveServiceMode NoneMode = new RecieveServiceMode() {
		@Override
		public void messageRecievedAction(Socket socket, InetAddress host) {}
	};
}
