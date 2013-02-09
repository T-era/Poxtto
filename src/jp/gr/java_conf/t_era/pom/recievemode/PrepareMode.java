package  jp.gr.java_conf.t_era.pom.recievemode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import jp.gr.java_conf.t_era.pom.common.transport.RecieveServiceMode;
import jp.gr.java_conf.t_era.pom.common.transport.TransportConsts;
import jp.gr.java_conf.t_era.pom.prepare.PrepareContext.PrepareModeObserver;

public class PrepareMode implements RecieveServiceMode {
	private final PrepareModeObserver observer;
	private final InetAddress budy;

	public PrepareMode(InetAddress budy, PrepareModeObserver observer) {
		this.budy = budy;
		this.observer = observer;
	}

	@Override
	public void messageRecievedAction(Socket socket, InetAddress budyHost) throws IOException {
		if (budy.equals(socket.getInetAddress())
				&& observer.isPreparing()) {

			InputStream input = socket.getInputStream();
			int x = input.read();
			if (x == TransportConsts.START_MESSAGE) {
				OutputStream output = socket.getOutputStream();

				output.write(TransportConsts.START_RESPONSE);
				output.flush();
				observer.callBackBudyReady();
			}
		} else {
			OutputStream output = socket.getOutputStream();
			output.write(TransportConsts.REFUSE_MESSAGE);
			output.flush();
		}
	}
}
