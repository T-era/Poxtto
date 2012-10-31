package ytel.pom.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public interface ServiceMode {
	boolean budyLocked();
	void messageRecievedAction(Socket socket, InetAddress host) throws IOException;

	static final ServiceMode NoneMode = new ServiceMode() {
		@Override
		public boolean budyLocked() {
			return false;
		}
		@Override
		public void messageRecievedAction(Socket socket, InetAddress host) {}
	};
}
