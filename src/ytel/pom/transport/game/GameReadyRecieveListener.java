package ytel.pom.transport.game;

import java.net.InetAddress;

import ytel.pom.transport.PingResponseListener;

public interface GameReadyRecieveListener extends PingResponseListener {
	public void BudyReadyRecieveAction(InetAddress hostName);
}
