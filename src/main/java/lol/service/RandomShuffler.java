package lol.service;

import java.util.Collections;
import java.util.List;

import lol.model.Cell;

public class RandomShuffler implements Shuffler {

	@Override
	public void shuffle(List<Cell> cells) {
		Collections.shuffle(cells);
	}
}
