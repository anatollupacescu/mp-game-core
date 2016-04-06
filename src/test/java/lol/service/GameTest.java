package lol.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lol.model.CapturingSession;
import lol.model.Player;

public class GameTest {

	@Test
	public void createGameWorks() {
		Player p1 = new Player(new CapturingSession(), "p1");
		Player p2 = new Player(new CapturingSession(), "p2");
		List<Player> playerList = Arrays.asList(p1, p2);
		int cellCount = 64;
		Game game = new InMemoryGame(cells -> {
		});
		game.startGame(playerList, cellCount);
		assertEquals(2, game.getPlayersCount());
		assertEquals(cellCount, game.getData().size());
		assertFalse(game.getWinner().isPresent());
		int p1cells = p1.getCellCount();
		game.checkCellById(p1, "0");
		assertEquals(p1cells - 1, p1.getCellCount());
		game.checkCellById(p1, "1");
		assertEquals(p1cells - 2, p1.getCellCount());
		game.checkCellById(p1, "1");
		assertEquals(p1cells - 2, p1.getCellCount());
		for (int i = 2; i < 21; i++) {
			assertFalse(game.getWinner().isPresent());
			game.checkCellById(p1, Integer.valueOf(i).toString());
		}
		assertEquals(0, p1.getCellCount());
		assertTrue(game.getWinner().isPresent());
		Player winner = game.getWinner().get();
		assertEquals(winner, p1);

		game = new InMemoryGame(cells -> {
		});
		game.startGame(playerList, cellCount);
		assertEquals(2, game.getPlayersCount());
		assertEquals(cellCount, game.getData().size());
		p1cells = p1.getCellCount();
		game.checkCellById(p1, "0");
		assertEquals(p1cells - 1, p1.getCellCount());
		game.checkCellById(p1, "1");
		assertEquals(p1cells - 2, p1.getCellCount());
		game.checkCellById(p1, "1");
		assertEquals(p1cells - 2, p1.getCellCount());
		for (int i = 2; i < 21; i++) {
			assertFalse(game.getWinner().isPresent());
			game.checkCellById(p1, Integer.valueOf(i).toString());
		}
		assertEquals(0, p1.getCellCount());
		assertTrue(game.getWinner().isPresent());
		winner = game.getWinner().get();
		assertEquals(winner, p1);
	}
}
