package ytel.pom.transport.menu;

import java.net.InetAddress;

import ytel.pom.transport.PingResponseListener;

public interface ShakeHandCompleteListener extends PingResponseListener {
	public void shakeHandCompleteAction(InetAddress hostName);
	public void pingTimeoutAction();
}
