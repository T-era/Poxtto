package ytel.pom.transport;

public interface PingResponseListener {
	void responseRecievedAction(String hoseName);
	void pingTimeoutAction();
}
