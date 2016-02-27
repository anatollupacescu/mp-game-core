package skeleton.stage;

import org.eclipse.jetty.websocket.api.Session;
import reactor.rx.action.Control;
import skeleton.bean.player.Player;
import skeleton.service.GameService;
import skeleton.service.MessageService;
import skeleton.service.PlayerService;

public class PlayerStage {

    private PlayerService playerService;
    private GameService gameService;
    private MessageService messageService;

    public void addPlayer(Session session, String name) {

        Player player = playerService.addPlayer(session, name);

        Control control = messageService.registerSession(player);

        player.setControl(control);

        if (gameService.isGameRunning()) {

            player.disableReadyButton();
        }

        messageService.broadcastPlayerList(playerService.getPlayerList());
    }

    public void removePlayer(Player player) {

        playerService.removePlayer(player);

        messageService.broadcastPlayerList(playerService.getPlayerList());

        if (gameService.isGameRunning()) {

            Player winner = gameService.getWinner();

            if (winner != null) {

                gameService.stopGame();

                messageService.broadcastWinner(winner);
            }

        } else if (playerService.isTheLastPlayerReady(player)) {

            startGame(gameService.getGameData());
        }
    }

    public void playerReady(Player player) {

        player.setReady(true);

        if (playerService.isTheLastPlayerReady(player)) {

            startGame(gameService.getGameData());

        } else {

            messageService.broadcastPlayerList(playerService.getPlayerList());
        }
    }

    protected void startGame(Object[] gameData) {

        gameService.startGame();

        messageService.broadcastGameTable(gameService.getGameData());
    }
}
