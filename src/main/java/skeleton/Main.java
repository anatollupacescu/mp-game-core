package skeleton;

import org.eclipse.jetty.websocket.api.Session;

public interface Main {

	void playerLogIn(Session session, String name);

	void playerLogOut(Session session);

	void playerReady(Session session);

	void playerClickedCell(Session session, String cellId);
}
