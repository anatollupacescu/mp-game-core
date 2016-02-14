package skeleton;

import java.util.Optional;

import org.eclipse.jetty.websocket.api.Session;

import reactor.rx.action.Control;
import skeleton.bean.client.ClientMessage;
import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;
import skeleton.bean.player.ReadyButton;
import skeleton.service.GameService;
import skeleton.service.MainService;
import skeleton.service.MessageService;
import skeleton.service.PlayerService;

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

	@Override
	public void playerLogIn(Session session, String name) {

		Optional<Player> playerOpt = playerService.getPlayerBySession(session);

		if (playerOpt.isPresent()) {

			messageService.sendMessage(session, ClientMessage.createAlert("Already logged in"));

			return;
		}

		if (name == null) {

			messageService.sendMessage(session, ClientMessage.createAlert("Name is mandatory"));

			return;
		}

		Player player = playerService.addPlayer(session, name);

		Control control = messageService.registerSession(player);

		player.setControl(control);

		if (gameService.isGameRunning()) {

			player.setReadyButtonState(ReadyButton.disabled);
		}

		messageService.broadcastPlayerList(playerService.getPlayerList());
	}

	@Override
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

	@Override
	public void playerReady(Player player) {

		if (player.isReady()) {

			messageService.sendMessage(player.getSession(), ClientMessage.createAlert("Invalid action"));

			return;
		}

		if (gameService.isGameRunning()) {

			messageService.sendMessage(player.getSession(), ClientMessage.createAlert("Game already started"));

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

	@Override
	public void playerClickedCell(Player player, Cell cell) {

		if (!gameService.isGameRunning()) {

			messageService.sendMessage(player, ClientMessage.createAlert("Game not started"));

			return;
		}

		if (!cell.getPlayer().equals(player)) {

			messageService.sendMessage(player, ClientMessage.createLog("Not your cell"));

			return;
		}

		gameService.markCell(player, cell);

		Player winner = gameService.getWinner();

		if (winner != null) {

			gameService.stopGame();

			messageService.broadcastWinner(winner);

		} else {

			messageService.broadcastMarkedCell(cell);
		}
	}
}
