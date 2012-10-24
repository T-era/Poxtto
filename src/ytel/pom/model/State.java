package ytel.pom.model;

public enum State {
	NotStarted(0),
	Connect(1),
	Waiting(2),
	Playing(3),
	Lose(4);

	private final int id;
	private State(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
