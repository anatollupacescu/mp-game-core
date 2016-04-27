package lol.model;

import java.util.List;

public class Player {

	private final transient Session session;
	
	private final String name;
	private Status status = Status.standby;
	private int cellCount = -1;
	private int color;
	
	public Player(Session session, String name) {
		this.session = session;
		this.name = name;
	}

	public void sendPlayerList(List<Player> playerList) {
		session.sendPlayerList(playerList);
	}

	public void sendGameData(List<Cell> data) {
		session.sendGameData(data);
	}

	public void sendErrorMessage(String string) {
		session.sendErrorMessage(string);
	}
	
	public void sendWinner(Player winner) {
		session.sendWinner(winner);
	}

	public void sendCheckedCell(Cell cell) {
		session.sendCheckedCell(cell);
	}
	
	public String getName() {
		return name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Session getSession() {
		return session;
	}

	public void setInitialCellCount(int cc) {
		this.cellCount = cc;
	}

	public void decrementCellCount() {
		cellCount -= 1;
	}
	
	public int getCellCount() {
		return cellCount;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (color != other.color)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (!session.equals(other.session))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", status=" + status + ", color=" + color + "]";
	}
}
