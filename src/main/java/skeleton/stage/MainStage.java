package skeleton.stage;

import org.eclipse.jetty.websocket.api.Session;
import skeleton.Main;
import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;
import skeleton.service.GameService;
import skeleton.service.MessageService;
import skeleton.service.PlayerService;

import java.util.Optional;

public class MainStage implements Main {

	private final MessageService messageService;

	private final PlayerService playerService;
	private final PlayerStage playerStage;

	private final GameService gameService;
	private final GameStage gameStage;

	public MainStage(PlayerService playerService, GameService gameService, MessageService messageService) {
		super();
		this.playerService = playerService;
		this.gameService = gameService;
		this.messageService = messageService;
		this.playerStage = new PlayerStage(playerService, gameService, messageService);
		this.gameStage = new GameStage(gameService, messageService);
	}

	public void playerLogIn(Session session, String name) {

		if (name == null) {

			messageService.alert(session, "Name is mandatory");

			return;
		}

		if (!playerService.isPlayerLoggedIn(session)) {

			playerStage.addPlayer(session, name);
		}
	}

	public void playerLogOut(Session session) {

		playerStage.removePlayer(session);
	}

	public void playerReady(Session session) {

		Optional<Player> playerOpt = playerService.getPlayerBySession(session);

		if (!playerOpt.isPresent()) {

			messageService.log(session, "Not logged in");

			return;
		}

		Player player = playerOpt.get();

		/* read */
		if (player.isReady()) {

			messageService.alert(player, "Invalid action");

			return;
		}

		/* read */
		if (gameService.isGameRunning()) {

			messageService.log(player, "Game already started");

			return;
		}

		/* commit */
		playerStage.playerReady(player);
	}

	public void playerClickedCell(Session session, String cellId) {

		Optional<Player> playerOpt = playerService.getPlayerBySession(session);

		if (!playerOpt.isPresent()) {

			messageService.log(session, "Not logged in");

			return;
		}

		Player player = playerOpt.get();

		/* read */
		if (!gameService.isGameRunning()) {

			messageService.alert(player, "Game not started");

			return;
		}

		Optional<Cell> cellOpt = gameService.getCellByIndex(cellId);

		if (!cellOpt.isPresent()) {

			messageService.log(player, "Not your cell");

			return;
		}

		Cell cell = cellOpt.get();

		/* read */
		if (!player.equals(cell.getPlayer())) {

			messageService.log(player, "Not your cell");

			return;
		}

		/* commit */
		gameStage.markCell(cell);
	}
}
