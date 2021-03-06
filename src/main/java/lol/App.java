package lol;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lol.model.Cell;
import lol.model.Player;
import lol.model.Session;
import lol.model.Status;
import lol.service.Game;
import lol.service.PlayerStore;

public class App {

	private final PlayerStore playerList;
	private final Game game;
	private final int size;

	private final Executor executor = Executors.newSingleThreadExecutor();

	public App(PlayerStore playerList, Game game, int size) {
		this.playerList = playerList;
		this.game = game;
		this.size = size;
	}

	public void connect(Session session) {
		if (!playerList.isEmpty()) {
			session.sendPlayerList(playerList.getPlayerList());
		}
		if (game.isRunning()) {
			session.sendGameData(game.getData());
		}
	}

	public void disconnect(Session session) {
		Optional<Player> playerOpt = playerList.lookupPlayerBySession(session);
		if(playerOpt.isPresent()) {
			executor.execute(() -> {
				playerList.removePlayerBySession(session);
				playerList.broadcastPlayerList();
				if (game.isRunning()) {
					game.dropPlayer(playerOpt.get());
					gameWinnerRoutine();
				}
			});
		} else {
			session.sendErrorMessage("Not connected");
		}
	}

	public void name(Session session, String name) {
		Optional<Player> player = playerList.lookupPlayerBySession(session);
		if (player.isPresent()) {
			player.get().sendErrorMessage("Please re-connect to get another name");
			return;
		}

		player = playerList.lookupPlayerByName(name);
		if (player.isPresent()) {
			player.get().sendErrorMessage(String.format("Name '%s' is already taken!", name));
			return;
		}

		executor.execute(() -> {
			playerList.savePlayer(session, name);
			playerList.broadcastPlayerList();
		});
	}

	public void ready(Session session) {
		playerList.lookupPlayerBySession(session).ifPresent(player -> {
			executor.execute(() -> {
				if (player.getStatus() != Status.ready) {
					player.setStatus(Status.ready);
					playerList.broadcastPlayerList();
					if (playerList.hasEnoughPlayers() && playerList.everyoneIsReady()) {
						game.startGame(playerList.getPlayerList(), size);
						playerList.bradcastGameData(game.getData());
						playerList.updateEveryonesStatusToPlaying();
						playerList.broadcastPlayerList();
					}
				}
			});
		});
	}

	public void cell(Session session, String id) {
		if (!game.isRunning()) {
			session.sendErrorMessage("Game is not running");
			return;
		}
		playerList.lookupPlayerBySession(session).ifPresent(player -> {
			game.lookupCellByPlayerAndId(player, id).ifPresent(cell -> {
				checkCell(cell, player);
			});
		});
	}

	private void checkCell(Cell cell, Player player) {
		if(player.equals(cell.getOwner()) && !cell.isChecked()) {
			executor.execute(() -> {
				game.checkCell(cell, player);
				playerList.broadcastCheckedCell(cell);
				gameWinnerRoutine();
			});		
		}
	}

	private void gameWinnerRoutine() {
		game.getWinner().ifPresent(winner -> {
			game.stopGame();
			playerList.broadcastWinner(winner);
			playerList.updateEveryonesStatusToStandBy();
			playerList.broadcastPlayerList();
		});
	}
}
