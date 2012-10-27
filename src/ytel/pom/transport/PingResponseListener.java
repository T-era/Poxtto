package ytel.pom.transport;

public interface PingResponseListener {
	void ResponseRecievedAction(String hoseName);
	void PingTimeoutAction();
}
