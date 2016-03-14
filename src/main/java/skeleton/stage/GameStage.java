package skeleton.stage;

import skeleton.bean.Cell;
import skeleton.service.GameService;
import skeleton.service.MessageService;

public class GameStage {

    private final GameService gameService;
    private final MessageService messageService;

    public GameStage(GameService gameService, MessageService messageService) {
		super();
		this.gameService = gameService;
		this.messageService = messageService;
	}

	public void markCell(Cell cell) {

        int cellId = gameService.markCell(cell);

        messageService.broadcastMarkedCell(cellId);

        gameService.getWinner().ifPresent(winner -> {

            gameService.stopGame();

            messageService.broadcastWinner(winner);
        });
    }
}
