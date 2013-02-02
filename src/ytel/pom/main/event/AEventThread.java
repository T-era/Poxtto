package ytel.pom.main.event;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import ytel.pom.main.ScoreView;
import ytel.pom.main.event.erase.EraseAnimation;
import ytel.pom.main.event.erase.EraseChecker;
import ytel.pom.main.event.rise.RiseAnimation;
import ytel.pom.main.model.damage.Damage;
import ytel.pom.main.model.damage.DamageStock;
import ytel.pom.main.model.field.PomField;
import ytel.pom.main.model.field.PomXY;

public class AEventThread {
	private final PomField field;
	private final DamageStock damageStock;
	private final EraseChecker checker;
	private final Stack<Integer> defList = new Stack<Integer>();
	private final ScoreView scoreKeeper;
	private Animation animation;
	boolean isDone = false;

	private int chain = 0;

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
			scoreKeeper.addScore(set.size() * 10 * (chain + 1)); // TODO Chain bonus
			chain ++;
		}

		return new EraseAnimation(field, set);
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
			return new RiseAnimation(field, checker, lines);
		}
	}
}
