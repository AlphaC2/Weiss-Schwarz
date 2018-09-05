package model.board;

import java.util.Collections;
import java.util.List;

import model.card.Card;

public class Library extends SearchableZone{
	private List<Card> cards;

	Library(List<Card> cards) {
		super("Library", false);
		for (Card card : this.cards) {
			card.flipFaceDown();
		}
	}
	
	public Card draw(){
		Card c = cards.get(0);
		cards.remove(c);
		return c;
	}
	
	void placeTop(Card c){
		cards.add(0, c);
	}
	
	void placeBottom(Card c){
		super.add(c);
	}
	
	public void shuffle(){
		Collections.shuffle(cards);
	}
}
