package game.model.board;

import game.model.card.Card;

public class Stock extends Zone{

	Stock() {
		super("Stock");
	}
	
	public Card pay(){
		Card c = cards.get(cards.size() - 1);
		cards.remove(c);
		return c;
	}
	
}
