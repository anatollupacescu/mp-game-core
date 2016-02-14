package skeleton.service;

import java.util.Optional;

import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;

public interface GameService {

	Player getWinner();

	boolean startGame();

	boolean stopGame();

	Object[] getGameData();

	boolean isGameRunning();

	void markCell(Player user, Cell cell);

	Optional<Cell> getCellById(String value);
}
