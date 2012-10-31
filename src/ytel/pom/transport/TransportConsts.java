package ytel.pom.transport;

public interface TransportConsts {
	public static final int SERVER_PORT = 57763;
//	public static final int PING_PORT = 57763;
	public static final int PING_SO_TIMEOUT = 5000;
	public static final int PING_MESSAGE = 63;

//	public static final int START_PORT = 50924;
	public static final int START_MESSAGE = 46;
	public static final int START_RESPONSE = 49;
	public static final int START_SO_TIMEOUT = 100000000;

	/**
	 * ゲーム中メッセージ
	 */
//	public static final int GAME_PORT = 58132;
	public static final int GAME_RESPONSE = 1;
	public static final int GAME_SO_TIMEOUT = 100;
}
