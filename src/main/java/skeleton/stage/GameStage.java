package skeleton.stage;

import skeleton.bean.game.Cell;
import skeleton.bean.player.Player;
import skeleton.service.GameService;
import skeleton.service.MessageService;

public class GameStage {

    private GameService gameService;
    private MessageService messageService;

    public void markCell(Cell cell) {

        gameService.markCell(cell);

        Player winner = gameService.getWinner();

        if (winner != null) {

            gameService.stopGame();

            messageService.broadcastWinner(winner);
        } else {

            messageService.broadcastMarkedCell(cell);
        }
    }
}
