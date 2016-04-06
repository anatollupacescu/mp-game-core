package lol.model;

import java.util.List;

public interface Session {

	void sendPlayerList(List<Player> playerList);

	void sendGameData(List<Cell> data);

	void sendErrorMessage(String string);

	void sendWinner(Player winner);

	void sendCheckedCell(Cell cell);
}
