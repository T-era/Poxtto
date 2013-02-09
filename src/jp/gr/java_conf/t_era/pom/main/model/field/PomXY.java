package jp.gr.java_conf.t_era.pom.main.model.field;

import jp.gr.java_conf.t_era.pom.main.model.pom.Pom;

public class PomXY {
	public final Pom pom;
	public final int cordX;
	public final int cordY;

	public PomXY(Pom pom, int cordX, int cordY) {
		this.pom = pom;
		this.cordX = cordX;
		this.cordY = cordY;
	}

	public PomXY moveBottom() {
		return new PomXY(this.pom, this.cordX, this.cordY - 1);
	}

	@Override
	public boolean equals(Object arg) {
		if (arg instanceof PomXY) {
			PomXY obj = (PomXY)arg;
			boolean eq = pom == obj.pom;
			assert eq
				&& (cordX != obj.cordX
					|| cordY != obj.cordY) : "？？";

			return eq;
		} else {
			return false;
		}
	}
	@Override
	public int hashCode() {
		return pom.hashCode();
	}
}
