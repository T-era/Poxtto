package ytel.pom.transport.menu;

import ytel.pom.transport.PingResponseListener;

public interface ShakeHandCompleteListener extends PingResponseListener {
	public void shakeHandCompleteAction(String hostName);
	public void pingTimeoutAction();
}
