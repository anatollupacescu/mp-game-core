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
}
