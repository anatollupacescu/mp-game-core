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

    public MainStage(PlayerService playerService, PlayerStage playerStage, GameService gameService, GameStage gameStage, MessageService messageService) {
        super();
        this.playerService = playerService;
        this.playerStage = playerStage;
        this.gameService = gameService;
        this.gameStage = gameStage;
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

        playerStage.addPlayer(session, name);
    }

    public void playerLogOut(Player player) {

        playerStage.removePlayer(player);
    }

    public void playerReady(Player player) {

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

    public void playerClickedCell(Player player, Cell cell) {

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
