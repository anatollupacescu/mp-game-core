package skeleton.service;

import java.util.Optional;

import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;

public interface GameService {

    Player getWinner();

    void startGame();

    void stopGame();

    boolean isGameRunning();

    Object[] getGameData();

    void markCell(Player user, Cell cell);

	Optional<Cell> getCellById(String value);
}
