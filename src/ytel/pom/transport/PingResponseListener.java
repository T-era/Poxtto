package ytel.pom.transport;

import java.net.InetAddress;

public interface PingResponseListener {
	void responseRecievedAction(InetAddress hoseName);
	void pingTimeoutAction();
}
