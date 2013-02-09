package  jp.gr.java_conf.t_era.pom.recievemode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import jp.gr.java_conf.t_era.pom.common.transport.RecieveServiceMode;
import jp.gr.java_conf.t_era.pom.common.transport.TransportConsts;
import jp.gr.java_conf.t_era.pom.main.GameControl;
import jp.gr.java_conf.t_era.pom.main.model.damage.Damage;

public class GameMode implements RecieveServiceMode {
	private final InetAddress budy;
	private final GameControl control;

	public GameMode(InetAddress budy, GameControl control) {
		this.budy = budy;
		this.control = control;
	}

	@Override
	public void messageRecievedAction(Socket socket, InetAddress budyHost) throws IOException {
		if (budy.equals(socket.getInetAddress())) {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			try {
				Damage dmg = (Damage)ois.readObject();

				OutputStream output = socket.getOutputStream();
				output.write(TransportConsts.START_RESPONSE);
				output.flush();

				control.stockDamage(dmg);
			} catch (ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			OutputStream output = socket.getOutputStream();
			output.write(TransportConsts.REFUSE_MESSAGE);
			output.flush();
		}
	}
}
