package jp.gr.java_conf.t_era.pom.main.model.field;

import java.util.Random;
import java.util.Set;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.model.pom.Pom;
import jp.gr.java_conf.t_era.pom.main.model.pom.PomColor;

public class FieldInitializer {
	private final int lines;
	private final GroupingStructer group;

	public FieldInitializer(int lines) {
		this.lines = lines;
		group = GroupingStructer.createForInit(lines);
	}

	public void attatch(Pom[][] poms) {
		Random rnd = MainContext.RANDOM;
		for (int y = 0; y < lines; y ++) {
			for (int x = 0; x < MainContext.WIDTH; x ++) {
				Set<PomColor> ignore = group.getColorsErasableFor(x, y);
				Pom pom = Pom.createInstance(PomColor.select(rnd, ignore));
				poms[y][x] = pom;

				group.addPom(x, y, pom);
			}
		}
	}
}
