package skeleton.service;

import org.eclipse.jetty.websocket.api.Session;
import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;

public interface MainService {

	void playerLogIn(Session session, String name);

	void playerLogOut(Player user);

	void playerReady(Player user);

	void playerClickedCell(Player user, Cell cell);
}
