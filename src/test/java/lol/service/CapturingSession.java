package lol.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lol.model.Cell;
import lol.model.Player;
import lol.model.Session;

public class CapturingSession implements Session {

	private Queue<Object> capture = new LinkedList<Object>();

	@Override
	public void sendPlayerList(List<Player> playerList) {
		System.out.println(playerList);
		this.capture.add(playerList);
	}

	@Override
	public void sendGameData(List<Cell> data) {
		System.out.println(data);
		this.capture.add(data);
	}

	@Override
	public void sendErrorMessage(String string) {
		System.out.println(string);
		this.capture.add(string);
	}

	@Override
	public void sendWinner(Player winner) {
		System.out.println(winner);
		this.capture.add(winner);
	}

	@Override
	public void sendCheckedCell(Cell cell) {
		System.out.println(cell);
		this.capture.add(cell);
	}
	
	public Object pop() {
		return capture.poll();
	}
}
