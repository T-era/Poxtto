package ytel.pom.main.model.damage;

import java.util.ArrayList;
import java.util.List;

public class DamageStock {
	private List<Damage> list = new ArrayList<Damage>();;

	public void recieve(Damage dmg) {
		list.add(dmg);
	}

	public List<Damage> flush() {
		List<Damage> temp = list;
		list = new ArrayList<Damage>();
		return temp;
	}
}
