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

    public boolean check() {
        if(checked) return false;
        checked = true;
        return true;
    }

	public boolean isChecked() {
		return checked;
	}
	
	public Player getOwner() {
		return owner;
	}

	public void decrementOwnersCount() {
		owner.decrementCellCount();
	}

	public boolean hasOwner() {
		return owner != null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
