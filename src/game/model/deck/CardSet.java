package game.model.deck;

import java.util.HashSet;
import java.util.Set;

import game.model.card.Card;

public class CardSet {
	private final Set<Card> cards;
	
	public CardSet(){
		cards = new HashSet<>();
	}
	
	public final Set<Card> getCards(){
		return cards;
	}
}
