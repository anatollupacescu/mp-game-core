package skeleton.service;

import org.eclipse.jetty.websocket.api.Session;
import skeleton.bean.player.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Player addPlayer(Session session, String name);

    List<Player> getPlayerList();

    void removePlayer(Player player);

    boolean isTheLastPlayerReady(Player player);

    Optional<Player> getPlayerBySession(Session session);
}
