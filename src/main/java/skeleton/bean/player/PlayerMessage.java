package skeleton.bean.player;

public class PlayerMessage {

	private final PlayerAction action;
	private final String value;

	public PlayerMessage(PlayerAction add, String value) {
		this.action = add;
		this.value = value;
	}

	public PlayerAction getAction() {
		return action;
	}

	public String getValue() {
		return value;
	}
}
