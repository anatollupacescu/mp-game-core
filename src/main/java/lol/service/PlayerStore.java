package lol.service;

import lol.model.Cell;
import lol.model.Player;
import lol.model.Session;

import java.util.List;
import java.util.Optional;

public interface PlayerStore {

    boolean isEmpty();

    List<Player> getPlayerList();

    void removePlayerBySession(Session session);

    void broadcastPlayerList();

    void broadcastWinner(Player winner);

    void resetReadyFlag();

    Optional<Player> lookupPlayerByName(String name);

    void savePlayer(Session session, String name);

    void bradcastGameData(List<Cell> data);

    void updateEveryonesStatusToPlaying();

    void broadcastCheckedCell(Cell cell);

    void updateEveryonesStatusToStandBy();

    Optional<Player> lookupPlayerBySession(Session name);

    boolean hasEnoughPlayers();

    boolean everyoneIsReady();
}
