package  jp.gr.java_conf.t_era.pom.common.transport.send;

import java.net.InetAddress;

import jp.gr.java_conf.t_era.pom.common.transport.TransportConsts;

public class PrepareSend {
	/**
	 * PING 発信
	 * @param host
	 */
	public static void putPing(InetAddress host, PingResponseListener listener) {
		Ping.sendIntMessage(host, TransportConsts.PING_SO_TIMEOUT, TransportConsts.PING_MESSAGE, TransportConsts.PING_MESSAGE, listener);
	}

	/**
	 * Prepare完了通信を送信
	 * @param host
	 */
	public static void putPrepare(InetAddress budyHost, PingResponseListener listener) {
		Ping.sendIntMessage(budyHost, TransportConsts.START_SO_TIMEOUT, TransportConsts.START_MESSAGE, TransportConsts.START_RESPONSE, listener);
	}

	public static <TMessage> void putAttack(InetAddress budyHost, TMessage dmg, PingResponseListener listener) {
		Ping.sendObjMessage(budyHost, TransportConsts.GAME_SO_TIMEOUT, dmg, TransportConsts.GAME_RESPONSE, listener);
	}
}
