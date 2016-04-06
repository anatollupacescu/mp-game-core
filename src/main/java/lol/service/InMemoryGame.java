package lol.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lol.model.Cell;
import lol.model.Player;

public class InMemoryGame implements Game {

	private List<Cell> cells;
	private List<Player> playerList;
	private Shuffler shuffler;

	public InMemoryGame(Shuffler shuffler) {
		this.shuffler = shuffler;
	}

	public void startGame(List<Player> gamePlayerList, int cellCount) {
		this.playerList = new ArrayList<>(gamePlayerList);
		this.cells = new ArrayList<>(cellCount);
		int cellsPerPlayer = cellCount / (gamePlayerList.size() + 1);
		for (Player player : playerList) {
			player.setInitialCellCount(cellsPerPlayer);
			for (int i = 0; i < cellsPerPlayer; i++) {
				Cell cell = new Cell(player);
				cells.add(cell);
			}
		}
		for (int i = cellsPerPlayer * gamePlayerList.size(); i < cellCount; i++) {
			Cell cell = new Cell();
			cells.add(cell);
		}
		shuffler.shuffle(cells);
	}

	public boolean isRunning() {
		return cells != null;
	}

	public List<Cell> getData() {
		return cells;
	}

	public int getPlayersCount() {
		return playerList.size();
	}

	public void stopGame() {
		cells = null;
	}

	public void dropPlayer(Player player) {
		playerList.remove(player);
	}

	public Optional<Cell> checkCellById(Player owner, String id) {
		Integer cellId = Integer.valueOf(id);
		Cell cell = cells.get(cellId);
		if (cell != null && !cell.isChecked() && cell.hasOwner()) {
			cell.check();
			cell.decrementOwnersCount();
			cell.setId(cellId);
			return Optional.of(cell);
		}
		return Optional.empty();
	}

	public Optional<Player> getWinner() {
		return playerList.stream().filter(player -> player.getCellCount() == 0).findFirst();
	}

}
