package model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.card.Card;

public class Library {
	private List<Card> cards;

	Library(List<Card> cards) {
		super();
		this.cards = new ArrayList<Card>(cards);
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
		cards.add(cards.size() - 1, c);
	}
	
	public void shuffle(){
		Collections.shuffle(cards);
	}
	
	void addCards(List<Card> passedCards){
		for (Card card : passedCards) {
			cards.add(card);
		}
	}
	
	public int size(){
		return cards.size();
	}
}
