package game.model.board;

import game.model.card.Card;
import game.model.card.Colour;

public class LevelZone extends SearchableZone {
	
	LevelZone() {
		super("Level", true);
	}

	boolean hasColour(Colour colour){
		for (Card card : cards) {
			if (card.getColour() == colour && card.isFaceUp())
				return true;
		}
		return false;
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
