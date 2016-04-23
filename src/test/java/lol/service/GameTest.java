package lol.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lol.App;
import lol.model.Player;

public class GameTest {

	@Test
	public void createGameWorks() {
		List<Player> players = null;
		App app = createApp();
		assertNotNull(app);
		/* no players present */
		connect(app, null, players);
		/* try illegal action */
		cell(app, null);
		/* try illegal action */
		disconnect(app, null);
		/* adding one player */
		players = new ArrayList<>();
		Player player = addPlayer(players, null, "p1");
		logIn(app, player, players);
		/* one player preset */
		connect(app, null, players);
		/* try illegal action */
		cell(app, null);
		/* try illegal action */
		disconnect(app, null);
		/* add player p2 */
		Player player2 = addPlayer(players, null, "p2");
		logIn(app, player2, players);
		assertEquals(players, ((CapturingSession)player.getSession()).pop());
		System.out.println("Checked>> " + players);
		/* two players preset */
		connect(app, null, players);
		/* two players preset */
		connect(app, null, players);
	}

	private void disconnect(App app, CapturingSession session) {
		if (session == null) {
			session = new CapturingSession();
		}
		app.disconnect(session);
		assertEquals("Not connected", session.pop());
		System.out.println("Checked>> Not connected");
	}

	private void cell(App app, CapturingSession session) {
		if (session == null) {
			session = new CapturingSession();
		}
		app.cell(session, "1");
		assertEquals("Game is not running", session.pop());
		System.out.println("Checked>> Game is not running");
	}

	private Player addPlayer(List<Player> players, CapturingSession session, String string) {
		if (session == null) {
			session = new CapturingSession();
		}
		Player player = new Player(session, string);
		players.add(player);
		player.setColor(players.size());
		return player;
	}

	private void logIn(App app, Player player, List<Player> players) {
		app.name(player.getSession(), player.getName());
		assertEquals(players, ((CapturingSession)player.getSession()).pop());
		System.out.println("Checked>> " + players);
	}

	private void connect(App app, CapturingSession session, List<Player> players) {
		if (session == null) {
			session = new CapturingSession();
		}
		app.connect(session);
		assertEquals(players, session.pop());
		System.out.println("Checked>> " + players);
	}

	private App createApp() {
		PlayerStore playerStore = new InMemoryPlayerStore();
		Game game = new InMemoryGame(new RandomShuffler());
		return new App(playerStore, game, 6);
	}
}
