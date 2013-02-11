package jp.gr.java_conf.t_era.pom.main.event;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import jp.gr.java_conf.t_era.pom.main.ScoreView;
import jp.gr.java_conf.t_era.pom.main.event.erase.EraseAnimation;
import jp.gr.java_conf.t_era.pom.main.event.erase.EraseChecker;
import jp.gr.java_conf.t_era.pom.main.event.rise.RiseAnimation;
import jp.gr.java_conf.t_era.pom.main.model.damage.Damage;
import jp.gr.java_conf.t_era.pom.main.model.damage.DamageStock;
import jp.gr.java_conf.t_era.pom.main.model.field.PomField;
import jp.gr.java_conf.t_era.pom.main.model.field.PomXY;

public class AEventThread {
	private final PomField field;
	private final DamageStock damageStock;
	private final EraseChecker checker;
	private final Stack<Integer> defList = new Stack<Integer>();
	private final ScoreView scoreKeeper;
	private Animation animation;
	boolean isDone = false;

	private int chain = 0;
	private int[] chainBonus = {
			1, 3, 5, 15
	}; // TODO Chain bonus

	AEventThread(PomField field, DamageStock damageStock, EraseChecker checker, ScoreView scoreKeeper) {
		this.field = field;
		this.damageStock = damageStock;
		this.checker = checker;
		this.scoreKeeper = scoreKeeper;
	}

	void appendDef(int score) {
		defList.push(score);
	}

	void aStep() {
		if (animation == null) {
			animation = createEraseAnimation();
		}
		if (animation == null) {
			animation = createRiseAnimation();
			if (animation == null) {
				isDone = true;
				return;
			}
		}

		animation.doAnMotion();
		if (animation.isDone()){
			animation = null;
			checker.refleshGroups();
		}
	}
	private EraseAnimation createEraseAnimation() {
		Set<PomXY> set = checker.getToErase();
		if (set.size() == 0) {
			chain = 0;

			if (defList.size() == 0) {
				return null;
			} else {
				int score = defList.pop();
				set = field.getUnder(Damage.scoreToLine(score));
			}
		} else {
			int bonus = getChainBonus();
			scoreKeeper.addScore(set.size() * bonus * (chain + 1));
			chain ++;
		}

		return new EraseAnimation(field, set);
	}
	private int getChainBonus() {
		if (chain >= chainBonus.length) {
			return chainBonus[chainBonus.length - 1];
		} else {
			return chainBonus[chain];
		}
	}

	private RiseAnimation createRiseAnimation() {
		List<Damage> list = damageStock.flush();
		if (list.size() == 0) {
			return null;
		} else {
			int lines = 0;
			for (Damage dmg : list) {
				lines += dmg.getLine();
			}
			if (lines > 0) {
				return new RiseAnimation(field, checker, lines);
			} else {
				return null;
			}
		}
	}
}
