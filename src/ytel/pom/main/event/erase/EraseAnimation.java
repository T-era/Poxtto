package ytel.pom.main.event.erase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ytel.pom.main.MainContext;
import ytel.pom.main.event.Animation;
import ytel.pom.main.model.field.PomField;
import ytel.pom.main.model.field.PomXY;
import ytel.pom.main.model.pom.Pom;

public class EraseAnimation implements Animation {
	private final List<PomXY> erasing = new ArrayList<PomXY>();
	private final List<PomXY> falling = new ArrayList<PomXY>();
	private final PomField poms;

	private boolean done;
	private int eraseCounter = 0;
	private int fallCounter = -1;

	public EraseAnimation(PomField poms, Set<PomXY> toErase) {
		this.poms = poms;
		for (PomXY temp : toErase) {
			temp.pom.setErasing();
			erasing.add(temp);
		}
	}

	public boolean isDone() {
		return done;
	}

	public void doAnMotion() {
		if (eraseCounter < 2) {
			// 消去
			for (PomXY temp : erasing) {
				temp.pom.setErasing();
			}
			eraseCounter ++;
			return;
		} else if (fallCounter == -1){
			// 消去　=> 落下
			for (PomXY temp : erasing) {
				poms.setAt(temp.cordX, temp.cordY, null);
			}
			boolean hasFalling = false;
			for (int x = 0; x < MainContext.WIDTH; x ++) {
				hasFalling |= setFalling(x);
			}
			if (hasFalling) {
				fallCounter = 0;
			} else {
				done = true;
			}
		} else {
			if (falling.isEmpty()) {
				done = true;
			} else {
				List<PomXY> transit = new ArrayList<PomXY>();
				for (PomXY temp : falling) {
					boolean isPeriod = temp.pom.fall(true);
					if (isPeriod) {
						transit.add(temp);
					}
				}
				for (PomXY temp : transit) {
					falling.remove(temp);
					poms.setAt(temp.cordX, temp.cordY, null);
					poms.setAt(temp.cordX, temp.cordY - 1, temp.pom);
					PomXY next = temp.moveBottom();
					if (next.cordY == 0
							|| (poms.at(next.cordX, next.cordY - 1) != null && ! poms.at(next.cordX, next.cordY - 1).isFalling())) {
						// 着地
						next.pom.setNotFalling();
					} else {
						falling.add(next);
					}
				}
			}
		}
	}

	/**
	 * 落下するPom判定
	 * 行番号の指定を受け、その行のPom全てについて、落下するかどうかを判定する。
	 * @param x
	 * @return
	 */
	private boolean setFalling(int x) {
		// 下から順検査し、空白より上にPomがあれば、それは落下する

		boolean hasFalling = false;
		boolean hasBlank = false;
		for (int y = 0; y < MainContext.HEIGHT; y ++) {
			if (poms.at(x, y) == null) {
				if (hasBlank) {
					;
				} else {
					hasBlank = true;
				}
			} else {
				if (hasBlank) {
					hasFalling = true;
					Pom temp = poms.at(x, y);
					temp.fall(false);
					falling.add(new PomXY(temp, x, y));
				}
			}
		}
		return hasFalling;
	}
}
