package ytel.pom.control;

import java.util.concurrent.CopyOnWriteArrayList;

import ytel.pom.model.Damage;

public class MainControlImpl {
	private final CopyOnWriteArrayList<Damage> damages;

	private boolean isFalling;

	public MainControlImpl() {
		damages = new CopyOnWriteArrayList<Damage>();
		isFalling = false;

	}
}
