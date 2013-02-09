package jp.gr.java_conf.t_era.pom.main.model.field;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.t_era.pom.main.model.pom.Pom;
import jp.gr.java_conf.t_era.pom.main.model.pom.PomColor;

public class PomGroup {
	private PomGroupMembers members;
	public final PomColor color;

	public PomGroup(PomColor color) {
		this.color = color;
		members = new PomGroupMembers();
	}
	public void add(Pom pom, int cordX, int cordY) {
		if (pom.color != this.color) throw new IllegalArgumentException();
for (PomXY mem : members.list) {
	if (mem.cordX == cordX && mem.cordY == cordY) {
		System.out.println("D" + cordX + ", " + cordY);
	}
}	
		members.add(new PomXY(pom, cordX, cordY));
	}
	public void merge(PomGroup anotherGroup) {
		if (anotherGroup.color != this.color) throw new IllegalArgumentException();
		members.addAll(anotherGroup.members);
		anotherGroup.members = this.members;
	}
	public List<PomXY> getMembers() {
		return members.list;
	}
	public int size() {
		return members.list.size();
	}
	@Override
	public String toString() {
		return color.toString();
	}

	private static class PomGroupMembers {
		private final List<PomXY> list = new ArrayList<PomXY>();
		private void add(PomXY member) {
			list.add(member);
		}
		private void addAll(PomGroupMembers arg) {
			list.addAll(arg.list);
		}
	}
}
