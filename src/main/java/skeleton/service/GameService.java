package skeleton.service;

import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;

import java.util.Optional;

public interface GameService {

	Player getWinner();

	boolean startGame();

	boolean stopGame();

	Object[] getGameData();

	boolean isGameRunning();

	void markCell(Player user, Cell cell);

	Optional<Cell> getCellById(String value);
}
