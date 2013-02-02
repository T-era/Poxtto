package ytel.pom.main.event.rise;

import ytel.pom.main.event.Animation;
import ytel.pom.main.event.erase.EraseChecker;
import ytel.pom.main.model.field.PomField;

public class RiseAnimation implements Animation {
	private final PomField field;
	private final EraseChecker checker;

	private boolean done;
	private int lineCounter;
	private int pictCounter;

	public RiseAnimation(PomField field, EraseChecker checker, int lineToRise) {
		lineCounter = lineToRise;
		this.field = field;
		this.checker = checker;
		field.riseALine(checker.getGroupingStructer());
		checker.refleshGroups();
	}

	public boolean isDone() {
		return done;
	}
	public void doAnMotion() {
		if (pictCounter > 2) {
			if (lineCounter > 0){
				pictCounter = 0;
				lineCounter --;
				field.riseALine(checker.getGroupingStructer());
				checker.refleshGroups();
			} else {
				field.notRising();
				done = true;
			}
		} else {
			field.setRise((double)pictCounter / 3.0);
			pictCounter ++;
		}
	}
}
