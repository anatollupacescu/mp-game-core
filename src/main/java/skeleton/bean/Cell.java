package skeleton.bean;

public class Cell {

	private final Player player;
	private boolean checked;

	public Cell(Player player) {
		this.player = player;
	}

	public boolean isChecked() {
		return checked;
	}

	public void check() {
		this.checked = true;
	}

	public Player getPlayer() {
		return player;
	}
}
