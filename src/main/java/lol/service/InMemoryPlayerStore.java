package lol.service;

import lol.model.Cell;
import lol.model.Player;
import lol.model.Session;
import lol.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPlayerStore implements PlayerStore {

	private final List<Player> playerList = new ArrayList<>();

	public boolean isEmpty() {
		return playerList.isEmpty();
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public Optional<Player> removePlayerBySession(Session session) {
		Optional<Player> player = lookupPlayerBySession(session);
		if(player.isPresent()) {
			playerList.remove(player);
			return player;
		}
		return Optional.empty();
	}

	public void broadcastPlayerList() {
		for(Player p : playerList) {
			p.sendPlayerList(getPlayerList());
		}
	}

	public void broadcastWinner(Player winner) {
		playerList.stream().forEach(player -> player.sendWinner(winner));
	}

	public void resetReadyFlag() {
		throw new IllegalStateException("Not implemented");
	}

	public Optional<Player> lookupPlayerByName(String name) {
		return playerList.stream().filter(player -> player.getName().equals(name)).findFirst();
	}

	@Override
	public void savePlayer(Session session, String name) {
		Player player = new Player(session, name);
		playerList.add(player);
		player.setColor(playerList.size());
	}

	public void bradcastGameData(List<Cell> data) {
		playerList.stream().forEach(player -> player.sendGameData(data));
	}

	public void updateEveryonesStatusToPlaying() {
		playerList.stream().forEach(player -> player.setStatus(Status.playing));
	}

	public void broadcastCheckedCell(Cell cell) {
		playerList.stream().forEach(player -> player.sendCheckedCell(cell));
	}

	public void updateEveryonesStatusToStandBy() {
		throw new IllegalStateException("Not implemented");
	}

	@Override
	public Optional<Player> lookupPlayerBySession(Session session) {
		return playerList.stream().filter(player -> player.getSession().equals(session)).findFirst();
	}

	@Override
	public boolean hasEnoughPlayers() {
		return playerList.size() > 1;
	}

	@Override
	public boolean everyoneIsReady() {
		for(Player player : playerList) {
			if(player.getStatus() != Status.ready) {
				return false;
			}
		}
		return true;
	}
}
