package ytel.pom.main.model.field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ytel.pom.main.MainContext;
import ytel.pom.main.model.pom.Pom;
import ytel.pom.main.model.pom.PomColor;

/**
 * 色別グループとして管理をします。
 * {隣と同色かどうか/消えるPomはどれか/(消えずに)配置できる色はどれか} といった問題を管理します。
 *
 * 落下などで大きく構成が変わる場合、このクラスのインスタンスは効力を失うため、再生成が必要になります。
 * (単純な積み上げなら、再生成は不要)
 *
 * @author Y.Terada
 *
 */
public class GroupingStructer {
	private final PomGroup[][] groupMap;
	private final int height;
	private final List<PomGroup> allGroups;

	/**
	 * 積み上げ、又はDamageによるRiseUpの場合。
	 * RiseUpの場合、field には消しこみが発生しないように積み上げられていること。
	 * @param field
	 * @return
	 */
	public static GroupingStructer createForPile(PomField field) {
		return new GroupingStructer(field);
	}
	public static GroupingStructer createForInit(int height) {
		return new GroupingStructer(height);
	}

	/**
	 * TODO
	 * 自らの消しこみによりRiseUpの場合。
	 * 即消しこみが発生することもある。
	 * @param field
	 * @return
	 */
	public static GroupingStructer createForRiseUp(PomField field) {
		return new GroupingStructer(field);
	}

	private GroupingStructer(int height) {
		this.height = height;
		this.groupMap = new PomGroup[height][MainContext.WIDTH];
		this.allGroups = new ArrayList<PomGroup>();
	}
	private GroupingStructer(PomField field) {
		this(field.heightAll);

		parseToGroup(field);
	}

	private void parseToGroup(PomField field) {
		for (int y = 0; y < height; y ++) {
			for (int x = 0; x < MainContext.WIDTH; x ++) {
				Pom pom = field.at(x, y);
				addAPom(x, y, pom, false);
			}
		}
	}

	public List<PomGroup> getGroupsBySize(int size) {
		List<PomGroup> ret = new ArrayList<PomGroup>();
		for (PomGroup g : allGroups) {
			if (g.size() >= size) {
				ret.add(g);
			}
		}
		return ret;
	}

	public void addPom(int x, int y, Pom pom) {
		addAPom(x, y, pom, true);
	}
	public Set<PomColor> getColorsErasableFor(int x, int y) {
		final Set<PomColor> list = new HashSet<PomColor>();

		class Checker {
			void addIfSizeEnough(PomGroup group) {
				if (group != null
						&& group.size() >= 2) {
					list.add(group.color);
				}
			}
			void addIfSameColor(PomGroup g1, PomGroup g2) {
				if (g1 != null && g2 != null
						&& g1.color == g2.color) {
					list.add(g1.color);
				}
			}
		}

		PomGroup bottom = y == 0 ? null : groupMap[y - 1][x];
		PomGroup left = x == 0 ? null : groupMap[y][x - 1];
		PomGroup right = x == MainContext.WIDTH - 1 ? null : groupMap[y][x + 1];

		Checker c = new Checker();
		c.addIfSizeEnough(bottom);
		c.addIfSizeEnough(left);
		c.addIfSizeEnough(right);
		c.addIfSameColor(bottom, left);
		c.addIfSameColor(bottom, right);
		c.addIfSameColor(left, right);
		return list;
	}

	private void addAPom(int x, int y, Pom thisOne, boolean checkRight) {
		PomGroup bottom = y == 0 ? null : groupMap[y - 1][x];
		PomGroup left = x == 0 ? null : groupMap[y][x - 1];
		PomGroup right = x == MainContext.WIDTH - 1 ? null : groupMap[y][x + 1];

		if (thisOne != null) {
			PomGroup thisGroup;
			PomColor thisColor = thisOne.color;
			if (isSameLeft(x, y, thisColor)
					&& isSameBottom(x, y, thisColor)) {
				thisGroup = left;
				thisGroup.merge(bottom);
			} else if (isSameLeft(x, y, thisColor)) {
				thisGroup = left;
			} else if (isSameBottom(x, y, thisColor)) {
				thisGroup = bottom;
			} else if (checkRight && isSameRight(x, y, thisColor)){
				thisGroup = right;
			} else {
				thisGroup = new PomGroup(thisColor);
				allGroups.add(thisGroup);
			}
			thisGroup.add(thisOne, x, y);
			groupMap[y][x] = thisGroup;
		}
	}

	private boolean isSameLeft(int x, int y, PomColor thisColor) {
		return x != 0 && groupMap[y][x - 1] != null
				&& thisColor == groupMap[y][x - 1].color;
	}
	private boolean isSameRight(int x, int y, PomColor thisColor) {
		return x != MainContext.WIDTH - 1 && groupMap[y][x + 1] != null
				&& thisColor == groupMap[y][x + 1].color;
	}
	private boolean isSameBottom(int x, int y, PomColor thisColor) {
		return y != 0 && groupMap[y - 1][x] != null
				&& thisColor == groupMap[y - 1][x].color;
	}

	Set<PomColor> getIgnoreForRise(int x, PomColor previous, PomColor previous2) {
		Set<PomColor> ret = new HashSet<PomColor>();
		PomGroup gHere = groupMap[0][x];
		if (gHere != null && gHere.size() >= 2) {
			ret.add(gHere.color);
		}
		if (x != 0) {
			PomGroup gLeft = groupMap[1][x - 1];
			if (gLeft != null && previous == gLeft.color) {
				ret.add(previous);
			}
			if (previous != null && previous2 != null
					&& previous == previous2) {
				ret.add(previous);		
			}
		}
		return ret;
	}
}
