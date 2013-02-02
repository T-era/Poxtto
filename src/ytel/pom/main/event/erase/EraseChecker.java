package ytel.pom.main.event.erase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ytel.pom.main.model.field.GroupingStructer;
import ytel.pom.main.model.field.PomField;
import ytel.pom.main.model.field.PomGroup;
import ytel.pom.main.model.field.PomXY;

public class EraseChecker {
	private final PomField field;
	private GroupingStructer groups;

	public EraseChecker(PomField field) {
		this.field = field;
	}

	public Set<PomXY> getToErase() {
		return groupToXY(this.groups.getGroupsBySize(3));
	}

	public void checkAdd(int x, int y1, int y2) {
		if (groups == null) groups = GroupingStructer.createForPile(field);

		groups.addPom(x, y1, field.at(x, y1));
		groups.addPom(x, y2, field.at(x, y2));
	}

	private Set<PomXY> groupToXY(List<PomGroup> group) {
		Set<PomXY> ret = new HashSet<PomXY>();
		for (PomGroup g : group) {
			ret.addAll(g.getMembers());
		}
		return ret;
	}

	public void refleshGroups() {
		this.groups = GroupingStructer.createForPile(field);
	}

	public GroupingStructer getGroupingStructer() {
		return groups;
	}
}
