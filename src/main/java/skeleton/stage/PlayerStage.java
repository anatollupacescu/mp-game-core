package skeleton.stage;

import org.eclipse.jetty.websocket.api.Session;

import reactor.rx.action.Control;
import skeleton.bean.player.Player;
import skeleton.service.GameService;
import skeleton.service.MessageService;
import skeleton.service.PlayerService;

public class PlayerStage {

	private final PlayerService playerService;
	private final GameService gameService;
	private final MessageService messageService;

	public PlayerStage(PlayerService playerService, GameService gameService, MessageService messageService) {
		super();
		this.playerService = playerService;
		this.gameService = gameService;
		this.messageService = messageService;
	}

	public void addPlayer(Session session, String name) {

		Player player = playerService.addPlayer(session, name);

		Control control = messageService.registerSession(player);

		player.setControl(control);

		if (gameService.isGameRunning()) {

			player.disableReadyButton();
		}

		messageService.broadcastPlayerList(playerService.getPlayerList());
	}

	public void removePlayer(Session playerSession) {

		playerService.getPlayerBySession(playerSession).ifPresent(player -> {

			playerService.removePlayer(player);

			gameService.dropPlayer(player);
		
			messageService.broadcastPlayerList(playerService.getPlayerList());
	
			if (gameService.isGameRunning()) {
	
				gameService.getWinner().ifPresent(winner -> {
	
					gameService.stopGame();
	
					messageService.broadcastWinner(winner);
				});
	
			} else if (playerService.allPlayersReady()) {
	
				startGame();
			}
		});
	}

	public void playerReady(Player player) {

		player.setReady(true);

		messageService.broadcastPlayerList(playerService.getPlayerList());

		if (playerService.allPlayersReady()) {

			startGame();
		}
	}

	protected void startGame() {

		gameService.startGame(playerService.getPlayerList());

		messageService.broadcastGameTable(gameService.getGameData());
	}
}
