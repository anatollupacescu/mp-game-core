package skeleton;

import org.eclipse.jetty.websocket.api.Session;
import reactor.rx.action.Control;
import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;
import skeleton.service.GameService;
import skeleton.service.MainService;
import skeleton.service.MessageService;
import skeleton.service.PlayerService;

import java.util.Optional;

public class Main implements MainService {

	private final MessageService messageService;
	private final PlayerService playerService;
	private final GameService gameService;

	public Main(PlayerService playerService, GameService gameService, MessageService messageService) {
		super();
		this.playerService = playerService;
		this.gameService = gameService;
		this.messageService = messageService;
	}

	public void playerLogIn(Session session, String name) {

		Optional<Player> playerOpt = playerService.getPlayerBySession(session);

		if (playerOpt.isPresent()) {

			messageService.alert(playerOpt.get(), "Already logged in");

			return;
		}

		if (name == null) {

			messageService.alert(playerOpt.get(), "Name is mandatory");

			return;
		}

		Player player = playerService.addPlayer(session, name);

		Control control = messageService.registerSession(player);

		player.setControl(control);

		if (gameService.isGameRunning()) {

			player.disableReadyButton();
		}

		messageService.broadcastPlayerList(playerService.getPlayerList());
	}

	public void playerLogOut(Player player) {

		Optional<Player> playerOpt = playerService.getPlayerBySession(player.getSession());

		if (playerOpt.isPresent()) {

			playerService.removePlayer(player);

			messageService.broadcastPlayerList(playerService.getPlayerList());

			if (gameService.isGameRunning()) {

				Player winner = gameService.getWinner();

				if (winner != null) {

					gameService.stopGame();

					messageService.broadcastWinner(winner);
				}

			} else if (playerService.isTheLastPlayerReady(player)) {

				gameService.startGame();

				messageService.broadcastGameTable(gameService.getGameData());
			}
		}
	}

	public void playerReady(Player player) {

		if (player.isReady()) {

			messageService.alert(player, "Invalid action");

			return;
		}

		if (gameService.isGameRunning()) {

			messageService.log(player, "Game already started");

			return;
		}

		player.setReady(true);

		if (playerService.isTheLastPlayerReady(player)) {

			gameService.startGame();

			messageService.broadcastGameTable(gameService.getGameData());

		} else {
			messageService.broadcastPlayerList(playerService.getPlayerList());
		}
	}

	public void playerClickedCell(Player player, Cell cell) {

		if (!gameService.isGameRunning()) {

			messageService.alert(player, "Game not started");

			return;
		}

		if (!cell.getPlayer().equals(player)) {

			messageService.log(player, "Not your cell");

			return;
		}

		gameService.markCell(player, cell);

		/* TODO move this to the implementation */
		Player winner = gameService.getWinner();

		if (winner != null) {

			if(gameService.stopGame()) {

				messageService.broadcastWinner(winner);
			}
		} else {

			messageService.broadcastMarkedCell(cell);
		}
	}
}
