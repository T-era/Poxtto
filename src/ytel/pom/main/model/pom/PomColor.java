package ytel.pom.main.model.pom;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public enum PomColor {
	BLUE(new Color(32, 96, 255)),
	RED(new Color(255, 64, 64)),
	YELLOW(new Color(224, 224, 0)),
	GREEN (new Color(0, 192, 0));

	public final Color color;

	private PomColor(Color color) {
		this.color = color;
	}

	public static PomColor select(Random rnd, Set<PomColor> ignore) {
		List<PomColor> selection = new ArrayList<PomColor>();
		for (PomColor c : PomColor.values()) {
			if (!ignore.contains(c)) {
				selection.add(c);
			}
		}

		int choice = rnd.nextInt(selection.size());
		return selection.get(choice);
	}
}
