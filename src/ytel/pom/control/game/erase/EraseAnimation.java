package ytel.pom.control.game.erase;

import java.util.ArrayList;
import java.util.List;

import ytel.pom.gui.main.Pom;

public class EraseAnimation {
	private final List<PomXY> erasing = new ArrayList<PomXY>();
	private final List<PomXY> falling = new ArrayList<PomXY>();
	private final Pom[][] poms;

	public boolean done;
	private int eraseCounter = 0;

	EraseAnimation(Pom[][] poms, List<PomXY> erased) {
		this.poms = poms;
		for (PomXY temp : erased) {
			temp.pom.setErasing();
			erasing.add(temp);
		}
	}
	public void doIt() {
		if (eraseCounter < 2) {
			for (PomXY temp : erasing) {
				temp.pom.setErasing();
			}
			eraseCounter ++;
			return;
		} else {
			for (PomXY temp : erasing) {
				poms[temp.cordY][temp.cordX] = null;
			}
			
			done = true;
		}
	}
}
