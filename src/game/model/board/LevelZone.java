package game.model.board;

import game.model.card.Card;

public class LevelZone extends SearchableZone {
	
	LevelZone() {
		super("Level", true);
	}
	
	int totalLevel(){
		int total = 0;
		for (Card card : cards) {
			if(card.isFaceUp())
				total += card.getLevel();
		}
		return total;
	}

	@Override
	protected LevelZone newInstance() {
		return new LevelZone();
	}
	
}
