package lol.model;

import java.util.List;

public class CapturingSession implements Session {

	private Object capture;

	public Object getCapture() {
		return capture;
	}

	@Override
	public void sendPlayerList(List<Player> playerList) {
		this.capture = playerList;
	}

	@Override
	public void sendGameData(List<Cell> data) {
		this.capture = data;
	}

	@Override
	public void sendErrorMessage(String string) {
		this.capture = string;
	}

	@Override
	public void sendWinner(Player winner) {
		this.capture = winner;
	}

	@Override
	public void sendCheckedCell(Cell cell) {
		this.capture = cell;
	}
}
