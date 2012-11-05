package ytel.pom.control.game.erase;

import java.util.ArrayList;
import java.util.List;

import ytel.pom.control.MainControl;
import ytel.pom.gui.main.Pom;

public class EraseAnimation {
	private final List<PomXY> erasing = new ArrayList<PomXY>();
	private final List<PomXY> falling = new ArrayList<PomXY>();
	private final Pom[][] poms;

	public boolean done;
	private int eraseCounter = 0;
	private int fallCounter = -1;

	EraseAnimation(Pom[][] poms, List<PomXY> erased) {
		this.poms = poms;
		for (PomXY temp : erased) {
			temp.pom.setErasing();
			erasing.add(temp);
		}
	}
	public void doIt() {
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
				poms[temp.cordY][temp.cordX] = null;
			}
			boolean hasFalling = false;
			for (int x = 0; x < MainControl.WIDTH; x ++) {
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
					poms[temp.cordY][temp.cordX] = null;
					poms[temp.cordY - 1][temp.cordX] = temp.pom;
					PomXY next = new PomXY(temp.pom, temp.cordX, temp.cordY - 1); // コンストラクタ別作成？
					if (next.cordY == 0
							|| (poms[next.cordY - 1][next.cordX] != null && ! poms[next.cordY - 1][next.cordX].isFalling())) {
						// 着地
						next.pom.setNotFalling();
					} else {
						falling.add(next);
					}
				}
			}
		}
	}
	private boolean setFalling(int x) {
		boolean hasFalling = false;
		boolean hasBlank = false;
		for (int y = 0; y < MainControl.HEIGHT; y ++) {
			if (poms[y][x] == null) {
				if (hasBlank) {
					;
				} else {
					hasBlank = true;
				}
			} else {
				if (hasBlank) {
					hasFalling = true;
					Pom temp = poms[y][x];
					temp.fall(false);
					falling.add(new PomXY(temp, x, y));
				}
			}
		}
		return hasFalling;
	}
}
