package jp.gr.java_conf.t_era.pom.main.model.pom;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

import jp.gr.java_conf.t_era.pom.main.MainContext;
import jp.gr.java_conf.t_era.pom.main.model.field.PomField;

public class NextPomGenerator {
	public final JPanel panel;
	private volatile Generator generator;

	public NextPomGenerator() {
		panel = new JPanel() {
			private static final long serialVersionUID = 2806265680816181653L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (generator != null) {
					generator.drawAll(g);
				}
			}
		};
		panel.setBackground(Color.BLACK);
	}
	public void initialize(int center, PomField field) {
		generator = new Generator(5, center, field);
	}
	public FallingPair getNext() {
		return generator.poll();
	}

	private class Generator {
	 	private final int center;
		private final PomField field;
		private final Queue<FallingPair> next = new LinkedList<FallingPair>();
	
		public Generator(int queueSize, int center, PomField field) {
			this.center = center;
			this.field = field;
	
			for (int i = 0; i < queueSize; i ++) {
				next.add(new FallingPair(center, MainContext.RANDOM, field));
			}
		}
	
		public FallingPair poll() {
			FallingPair p = next.poll();
			next.add(new FallingPair(center, MainContext.RANDOM, field));
			return p;
		}
	
		public void drawAll(Graphics g) {
			int i = 0;
			for (FallingPair pair : next) {
				pair.drawPairInLeft(g, i * (MainContext.POM_SIZE_H * 2 + 3));
				i++;
			}
		}
	}
}
