package jp.gr.java_conf.t_era.pom.main;

import java.awt.Graphics;
import java.net.InetAddress;

import jp.gr.java_conf.t_era.pom.common.transport.send.PrepareSend;
import jp.gr.java_conf.t_era.pom.main.event.EventManager;
import jp.gr.java_conf.t_era.pom.main.model.damage.Damage;
import jp.gr.java_conf.t_era.pom.main.model.damage.DamageStock;
import jp.gr.java_conf.t_era.pom.main.model.field.PomField;
import jp.gr.java_conf.t_era.pom.main.model.pom.FallingPair;
import jp.gr.java_conf.t_era.pom.main.model.pom.NextPomGenerator;
import jp.gr.java_conf.t_era.pom.prepare.PrepareContext;

public class GameControl {
	private final PrepareContext pContext;
	private final PomField field;
	private final NextPomGenerator nextGenerator;
	private final ScoreView scoreKeeper;
	private final EventManager event;
	private final InetAddress budyHost;
	private final DamageStock damageStock;
	private FallingPair pair;

	public GameControl(PrepareContext pContext, PomField field, NextPomGenerator nextGenerator, DamageStock damageStock, ScoreView scoreView, InetAddress budyHost) {
		this.pContext = pContext;
		this.budyHost = budyHost;
		this.field = field;
		this.damageStock = damageStock;
		event = new EventManager(pContext.mContext, field, damageStock, scoreView);
		nextGenerator.initialize(MainContext.CENTER_X, field);
		this.nextGenerator = nextGenerator;
		this.pair = nextGenerator.getNext();
		this.scoreKeeper = scoreView;
	}

	public synchronized void moveRight() {
		if (! event.isOn()) {
			pair.MoveRiht();
		}
	}

	public synchronized void moveLeft() {
		if (! event.isOn()) {
			pair.MoveLeft();
		}
	}

	public synchronized void moveDown() {
		toDown();
	}

	public synchronized void moveReverse() {
		if (! event.isOn()) {
			pair.MoveReverse();
		}
	}

	public synchronized void flashAtt() {
		Damage dmg = scoreKeeper.flushToDamage();
		PrepareSend.putAttack(budyHost, dmg, pContext.budyLostRefresh);
	}

	public synchronized void flashDef() {
		event.appendDef(scoreKeeper.flushDamage());
	}

	public synchronized void fallDown() {
		toDown();
	}
	private void toDown() {
		if (! event.isOn()) {
			boolean landed = pair.MoveDown();
			if (landed) {
				int cordY1 = MainContext.HEIGHT - pair.getY() / MainContext.POM_SIZE_H - 2;
				int cordY2 = MainContext.HEIGHT - pair.getY() / MainContext.POM_SIZE_H - 1;
				event.startNewEvent(pair.getCordX(), cordY1, cordY2);

				pair = nextGenerator.getNext();
			}
		}
	}

	public synchronized void eventTick() {
		if (event.isOn()) {
			event.aStep();
		}
	}

	public synchronized void stockDamage(Damage dmg) {
		damageStock.recieve(dmg);
	}
	public void drawAll(Graphics g) {
		field.draw(g);
		pair.drawPair(g);
	}
}
