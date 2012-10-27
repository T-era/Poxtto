package ytel.pom.control;

import java.util.ArrayList;
import java.util.List;

import ytel.pom.gui.main.Pom;
import ytel.pom.model.Damage;

public class MainControlEvent {
	private final Pom[][] poms;
	private final List<Damage> list;
	private EraseHelper eraseHelper = null;

	public MainControlEvent(Pom[][] poms, List<Damage> list) {
		this.poms = poms;
		this.list = list;
	}

	public boolean next() {
		if (eraseHelper == null) {
			eraseHelper = new EraseHelper();
		} else if (! eraseHelper.done) {
			eraseHelper.doIt();
		} else {
			// ダメージ送受信
		}
		// 終了判定

		return true;
	}

	private class EraseHelper {
		private boolean done = false;
		private int count = 0;
		private List<Pom> erased = new ArrayList<Pom>();
		/**
		 * 消去のためのマークアップをします。
		 */
		public EraseHelper() {
			// TODO 消去
		}
		public void doIt() {
			if (++count == 5) {
				done = true;
			}
		}
	}
}
