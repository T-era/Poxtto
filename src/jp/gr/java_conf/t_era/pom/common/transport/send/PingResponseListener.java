package  jp.gr.java_conf.t_era.pom.common.transport.send;

import java.net.InetAddress;

public interface PingResponseListener {
	void responseRecievedAction(InetAddress hoseName);
	void pingTimeoutAction();

	static PingResponseListener NONE = new PingResponseListener() {
		@Override
		public void responseRecievedAction(InetAddress host) { }

		@Override
		public void pingTimeoutAction() { }
	};
}
