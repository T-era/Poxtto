package jp.gr.java_conf.t_era.pom.main.event;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.ScoreView;
import jp.gr.java_conf.t_era.pom.main.event.erase.EraseChecker;
import jp.gr.java_conf.t_era.pom.main.model.damage.DamageStock;
import jp.gr.java_conf.t_era.pom.main.model.field.PomField;

public class EventManager {
	private final DamageStock damageStock;
	private final MainContext mContext;
	private final PomField field;
	private final EraseChecker eraseChecker;
	private final ScoreView scoreKeeper;

	private AEventThread aThread;

	public EventManager(MainContext mContext, PomField field, DamageStock damageStock, ScoreView scoreKeeper) {
		this.mContext = mContext;
		this.damageStock = damageStock;
		this.scoreKeeper = scoreKeeper;
		this.field = field;
		this.eraseChecker = new EraseChecker(field);
	}

	public boolean isOn() {
		return aThread != null;
	}

	public void startNewEvent(int cordX, int cordY1, int cordY2) {
		eraseChecker.checkAdd(cordX, cordY1, cordY2);
		aThread = new AEventThread(field, damageStock, eraseChecker, scoreKeeper);
	}

	public void aStep() {
		aThread.aStep();
		if (aThread.isDone) {
			eraseChecker.refleshGroups();
			aThread = null;

			if (field.isOverflow()) {
				mContext.doneAGame();
			}
		}
	}

	/**
	 * ブロック削除イベントを追加。
	 * イベントを開始していなければ即開始。
	 * @param score
	 */
	public void appendDef(int score) {
		if (aThread == null) {
			aThread = new AEventThread(field, damageStock, eraseChecker, scoreKeeper);
			aThread.appendDef(score);
		} else {
			aThread.appendDef(score);
		}
	}
}
