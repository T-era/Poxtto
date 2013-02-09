package  jp.gr.java_conf.t_era.pom.prepare;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import jp.gr.java_conf.t_era.pom.ApplicationContext;
import jp.gr.java_conf.t_era.pom.ApplicationContext.Mode;
import jp.gr.java_conf.t_era.pom.common.transport.RecieveService;
import jp.gr.java_conf.t_era.pom.common.transport.send.PingResponseListener;
import jp.gr.java_conf.t_era.pom.common.transport.send.PrepareSend;
import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.recievemode.PrepareMode;

public class PrepareContext {
	private final ApplicationContext parent;
	private final InetAddress budyHost;
	public final MainContext mContext;

	public final PingResponseListener budyLostRefresh = new PingResponseListener() {
		@Override
		public void responseRecievedAction(InetAddress hoseName) {
			// Do nothing
		}

		@Override
		public final void pingTimeoutAction() {
			parent.restart(false);
		}
	};

	private volatile boolean isPreparing = true;
	private volatile boolean selfReady;
	private volatile boolean budyReady;

	public PrepareContext(ApplicationContext parent, InetAddress budyHost) {
		this.parent = parent;
		this.budyHost = budyHost;
		RecieveService service = parent.recieve;
		mContext = new MainContext(parent, this, budyHost);

		service.setMode(new PrepareMode(budyHost, new PrepareModeObserver()));
	}

	public class PrepareModeObserver {
		public boolean isPreparing() {
			return isPreparing;
		}

		public void callBackBudyReady() {
			budyReady = true;
			checkBoth();
		}
	}

	private void checkBoth() {
		if (! isPreparing) return;
		if (selfReady && budyReady) {
			isPreparing = false;
		}
		if (!isPreparing) {
			parent.prepareComplete(mContext);
		}
	}

	public MouseListener toPrepareMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Mode mode = parent.getMode();
				if (mode == Mode.Preparing) {
					sendMessage();
					selfReady = true;
					checkBoth();
				} else if (mode == Mode.Done) {
// TODO think. how to sync with budy??
//					if (e.getButton() == MouseEvent.BUTTON3) {
//						parent.restart(true);
//					} else {
						parent.restart(false);
//					}
				}
			}
			private void sendMessage() {
				PrepareSend.putPrepare(budyHost, new PingResponseListener() {
					@Override
					public void responseRecievedAction(InetAddress hoseName) { }
					@Override
					public void pingTimeoutAction() {
						if (! budyReady) {
							JOptionPane.showMessageDialog(null, "timeout.");
						}
					}
				});
			}
		};
	}
}
