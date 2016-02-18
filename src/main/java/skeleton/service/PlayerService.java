package skeleton.service;

import org.eclipse.jetty.websocket.api.Session;
import skeleton.bean.player.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

	Player addPlayer(Session session, String name);

	void removePlayer(Player Player);

	boolean isTheLastPlayerReady(Player player);

	Optional<Player> getPlayerBySession(Session session);

	List<Player> getPlayerList();
}
