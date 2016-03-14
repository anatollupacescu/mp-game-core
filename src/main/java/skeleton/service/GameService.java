package skeleton.service;

import java.util.List;
import java.util.Optional;

import skeleton.bean.Cell;
import skeleton.bean.Player;

public interface GameService {

    Optional<Player> getWinner();

    void stopGame();

    boolean isGameRunning();

    List<Cell> getGameData();

    int markCell(Cell cell);

	void startGame(List<Player> playerList);

	Optional<Cell> getCellByIndex(String id);

	void dropPlayer(Player player);
}
