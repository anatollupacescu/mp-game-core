package knowledge;

import lol.model.Player;
import lol.model.Session;

import java.util.List;

public class GamePage {

    private ReadyButton ready;
    private List<Player> playerList;
    private String name;
    private EnterButton enterButton;

    private GameArea gameArea;
    private Session playerSession;

    public void onEnter() {
        enterButton.hide();
        //add assertions
    }

    public void onReadyClick() {
        ready.hide();
    }

    public void onCellClick() {

    }
}
