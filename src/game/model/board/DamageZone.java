package game.model.board;

import game.model.card.Card;
import game.model.card.Colour;

public class DamageZone extends SearchableZone {
	public static final int cardsPerLevel = 7;
	
	DamageZone() {
		super("Damage", true);
	}
	
	public Card removeFromBottom(){
		Card c = cards.get(0);
		cards.remove(c);
		return c;
	}
	
	boolean hasColour(Colour colour){
		for (Card card : cards) {
			if(card.getColour() == colour && card.isFaceUp())
				return true;
		}
		return false;
	}

	public DamageZone newInstance() {
		return new DamageZone();
	}
	
}
