package ytel.pom.control.game.erase;

import java.util.ArrayList;
import java.util.List;

import ytel.pom.control.MainControl.AllColor;
import ytel.pom.gui.main.Pom;

public class GroupEraser {
	private Pom[][] map;
	/**
	 * 消去のためのマークアップをします。
	 */
	public GroupEraser(Pom[][] map) {
		this.map = map;
	}
	public EraseAnimation erase() {
		List<PomGroup> erasing = whichErasing();
		List<PomXY> erased = new ArrayList<PomXY>();
		for (PomGroup group : erasing) {
			for (PomXY pomIn : group.members) {
//				map[pomIn.cordY][pomIn.cordX] = null;
				erased.add(pomIn);
			}
		}

		if (erasing.isEmpty()) return null;
		return new EraseAnimation(map, erased);
	}

	private boolean isSameLeft(int x, int y, AllColor thisColor) {
		return x != 0 && map[y][x - 1] != null
				&& thisColor == map[y][x - 1].getColor();
	}
	private boolean isSameBottom(int x, int y, AllColor thisColor) {
		return y != 0 && map[y-1][x] != null
				&& thisColor == map[y-1][x].getColor();
	}
	private List<PomGroup> whichErasing() {
		PomGroup[][] groupMap = new PomGroup[map.length][map[0].length];
		List<PomGroup> allGroups = new ArrayList<PomGroup>();
		for (int y = 0; y < map.length; y ++) {
			for (int x = 0; x < map[y].length; x ++) {
				Pom thisOne = map[y][x];
				if (thisOne != null) {
					AllColor thisColor = thisOne.getColor();
					if (isSameLeft(x, y, thisColor)
							&& isSameBottom(x, y, thisColor)) {
						PomGroup group = groupMap[y][x-1];
						PomGroup group2 = groupMap[y-1][x];
						group.add(thisOne, x, y);
						group.merge(group2);
						for (PomXY temp : group2.members) {
							groupMap[temp.cordY][temp.cordX] = group;
						}
					} else if (isSameLeft(x, y, thisColor)) {
						PomGroup group = groupMap[y][x-1];
						group.add(thisOne, x, y);
						groupMap[y][x] = group;
					} else if (isSameBottom(x, y, thisColor)) {
						PomGroup group = groupMap[y-1][x];
						group.add(thisOne,  x,  y);
						groupMap[y][x] = group;
					} else {
						PomGroup group = new PomGroup(thisColor);
						allGroups.add(group);
						group.add(thisOne, x, y);
						groupMap[y][x] = group;
					}
				}
			}
		}
		List<PomGroup> ret = new ArrayList<PomGroup>();
		for (PomGroup group : allGroups) {
			if (group.size() >= 3) {
				ret.add(group);
			}
		}
		return ret;
	}

	private static class PomGroup {
		private final List<PomXY> members;
		private final AllColor color;

		public PomGroup(AllColor color) {
			this.color = color;
			members = new ArrayList<PomXY>();
		}
		public void add(Pom pom, int cordX, int cordY) {
			if (pom.getColor() != this.color) throw new IllegalArgumentException();
			members.add(new PomXY(pom, cordX, cordY));
		}
		public void merge(PomGroup anotherGroup) {
			if (anotherGroup.color != this.color) throw new IllegalArgumentException();
			members.addAll(anotherGroup.members);
		}
		public int size() {
			return members.size();
		}
	}
}
