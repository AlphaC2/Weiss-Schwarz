package model.board;

import model.card.Card;
import model.card.Colour;

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
	
}
