package skeleton.service;


import org.eclipse.jetty.websocket.api.Session;
import reactor.rx.action.Control;
import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;

import java.util.List;

public interface MessageService {

	void broadcastPlayerList(List<Player> playerList);

	void broadcastGameTable(List<Cell> gameData);

	void broadcastMarkedCell(Cell cell);

	void broadcastWinner(Player winner);

	Control registerSession(Player player);

	void alert(Player player, String message);

	void log(Player player, String message);

    void sendPlayerList(Session session, List<Player> playerList);

	void alert(Session session, String message);

	void log(Session session, String message);
}
