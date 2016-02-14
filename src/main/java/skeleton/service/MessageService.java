package skeleton.service;

import java.util.List;

import org.eclipse.jetty.websocket.api.Session;

import reactor.rx.action.Control;
import skeleton.bean.client.ClientMessage;
import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;

public interface MessageService {

	void broadcastPlayerList(List<Player> playerList);

	void broadcastGameTable(Object[] gameData);

	void broadcastMarkedCell(Cell cell);

	void broadcastWinner(Player winner);

	void sendMessage(Session session, ClientMessage<?> message);

	void sendMessage(Player player, ClientMessage<?> message);

	Control registerSession(Player player);
}
