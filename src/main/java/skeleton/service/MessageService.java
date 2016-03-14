package skeleton.service;


import java.util.List;

import org.eclipse.jetty.websocket.api.Session;

import skeleton.bean.Cell;
import skeleton.bean.Player;

public interface MessageService {

	void broadcastPlayerList(List<Player> playerList);

	void broadcastGameTable(List<Cell> gameData);

	void broadcastMarkedCell(int cellId);

	void broadcastWinner(Player winner);

	void alert(Player player, String message);

	void log(Player player, String message);

    void sendPlayerList(Session session, List<Player> playerList);

	void alert(Session session, String message);

	void log(Session session, String message);
}
