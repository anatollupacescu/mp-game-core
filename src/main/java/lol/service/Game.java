package lol.service;

import lol.model.Cell;
import lol.model.Player;

import java.util.List;
import java.util.Optional;

public interface Game {

    boolean isRunning();

    List<Cell> getData();

    int getPlayersCount();

    void stopGame();

    void startGame(List<Player> gamePlayerList, int cellCount);

    void dropPlayer(Player player);

    Optional<Cell> checkCellById(Player owner, String id);

    Optional<Player> getWinner();
}
