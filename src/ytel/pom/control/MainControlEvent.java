package ytel.pom.control;

import java.util.List;

import ytel.pom.control.game.erase.EraseAnimation;
import ytel.pom.control.game.erase.GroupEraser;
import ytel.pom.gui.main.Pom;
import ytel.pom.model.Damage;

public class MainControlEvent {
	private final Pom[][] poms;
	private final List<Damage> list;
	private EraseAnimation eraser = null;

	public MainControlEvent(Pom[][] poms, List<Damage> list) {
		this.poms = poms;
		this.list = list;
	}

	public boolean next() {
		if (eraser == null) {
			eraser = new GroupEraser(poms).erase();
			if (eraser == null) {
				// 終了
				return true;
			}
		} else if (eraser.done) {
			// ダメージ送受信
			eraser = null;
		} else {
			eraser.doIt();
		}

		// 継続
		return false;
	}
}
