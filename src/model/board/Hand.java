package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Climax;

public class Hand {
	private List<Card> cards;
	
	Hand() {
		super();
		cards = new ArrayList<Card>();
	}

	void add(Card c){
		cards.add(c);
	}
	

	Card get(Card c) {
		cards.remove(c);
		return c;
	}
	
	public List<Card> getCards(){
		return new ArrayList<>(cards);
	}

	int size() {
		return cards.size();
	}
	
	@Override
	public String toString(){
		String s = "";
		if (getCards().size() == 0)
			return "Empty";
		for (Card c : getCards()) {
			s+= c + System.lineSeparator();
		}
		return s;
	}

	public void remove(Card c) {
		if (!cards.remove(c)){
			throw new IllegalArgumentException("Card not in hand " + System.lineSeparator() + c);
		}
	}

}
