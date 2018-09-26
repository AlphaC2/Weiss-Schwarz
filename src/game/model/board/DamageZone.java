package game.model.board;

import game.model.card.Card;

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
	
	public DamageZone newInstance() {
		return new DamageZone();
	}
	
}
