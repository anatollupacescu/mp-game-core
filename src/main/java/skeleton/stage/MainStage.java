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
		this.playerStage = new PlayerStage();
		this.gameService = gameService;
		this.gameStage = new GameStage();
		this.messageService = messageService;
	}

	public void playerLogIn(Session session, String name) {

		if (name == null) {

			messageService.alert(session, "Name is mandatory");

			return;
		}

		Optional<Player> playerOpt = playerService.getPlayerBySession(session);

		if (playerOpt.isPresent()) {

			messageService.alert(playerOpt.get(), "Already logged in");

			return;
		}

		playerStage.addPlayer(session, name);
	}

	public void playerLogOut(Session session) {

		playerService.getPlayerBySession(session).ifPresent(player -> {

			playerStage.removePlayer(player);
		});
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

		Optional<Cell> cellOpt = gameService.getCellById(cellId);

		if (!cellOpt.isPresent()) {

			messageService.log(player, "Not your cell");

			return;
		}

		Cell cell = cellOpt.get();

		/* read */
		if (!gameService.isGameRunning()) {

			messageService.alert(player, "Game not started");

			return;
		}

		/* read */
		if (!cell.getPlayer().equals(player)) {

			messageService.log(player, "Not your cell");

			return;
		}

		/* commit */
		gameStage.markCell(player, cell);
	}
}
