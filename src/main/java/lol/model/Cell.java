package lol.model;

public class Cell {

    private final Player owner;
    private int id;
    private boolean checked;

    public Cell() {
        this.owner = null;
    }

    public Cell(Player player) {
        this.owner = player;
    }

    public void check() {
        checked = true;
    }

	public boolean isChecked() {
		return checked;
	}
	
	public Player getOwner() {
		return owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
