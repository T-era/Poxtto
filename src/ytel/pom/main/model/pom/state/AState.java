package ytel.pom.main.model.pom.state;

import java.awt.Graphics;

import ytel.pom.main.model.pom.PomColor;

public interface AState {
	void drawImpl(Graphics g, int x, int y, PomColor c);
}
