package ytel.pom.transport.game;

import ytel.pom.transport.PingResponseListener;

public interface GameReadyRecieveListener extends PingResponseListener {
	public void BudyReadyRecieveAction(String hostName);
}
