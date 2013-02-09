package jp.gr.java_conf.t_era.pom;

import java.net.InetAddress;

import jp.gr.java_conf.t_era.pom.common.transport.RecieveService;
import jp.gr.java_conf.t_era.pom.main.MainContext;
import  jp.gr.java_conf.t_era.pom.prepare.PrepareContext;
import jp.gr.java_conf.t_era.pom.recievemode.ShakingHandMode;

public class ApplicationContext {
	public final ApplicationFrame frame = new ApplicationFrame();
	public final RecieveService recieve = RecieveService.service;
	private Mode mode = null;
	private InetAddress budyHost;
	private PrepareContext pContext;

	public static void main(String[] args) {
		new ApplicationContext().start();
	}
	public void start() {
		mode = Mode.Connecting;
		recieve.setMode(new ShakingHandMode(this));
		frame.setConnectMode(this);
	}

	/**
	 * 発行したPingに応答があった場合
	 * またはPing受信した場合
	 *
	 * @param budyHost
	 */
	public void shakeHandComplete(InetAddress budyHost) {
		mode = Mode.Preparing;
		this.budyHost = budyHost;
		synchronized (this) {
			if (pContext == null) {
				pContext = new PrepareContext(this, budyHost);
				frame.setPrepareeMode(this, pContext, pContext.mContext.keyDelegate);
			}
		}
		frame.repaint();
	}

	/**
	 * 双方準備が完了した場合
	 *
	 * @param budyHost
	 */
	public void prepareComplete(MainContext mContext) {
		mode = Mode.Running;
		mContext.start(recieve, budyHost);
	}

	public void gameDone() {
		mode = Mode.Done;
		frame.repaint();
	}
	public void restart(boolean keepConnect) {
		if (keepConnect) {
			start();
		} else {
			shakeHandComplete(budyHost);
		}
	}

	public Mode getMode() {
		return mode;
	}

	public enum Mode {
		Connecting, Preparing, Running, Done
	}
}
