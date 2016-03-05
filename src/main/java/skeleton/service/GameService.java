package skeleton.service;

import java.util.List;
import java.util.Optional;

import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;

public interface GameService {

    Player getWinner();

    void stopGame();

    boolean isGameRunning();

    List<Cell> getGameData();

    void markCell(Cell cell);

	void startGame(List<Player> playerList);

	Optional<Cell> getCellByIndex(String id);
}
