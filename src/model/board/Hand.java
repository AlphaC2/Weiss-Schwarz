package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class Hand {
	private List<Card> cards;
	
	Hand() {
		super();
		cards = new ArrayList<Card>();
	}

	void add(Card c){
		cards.add(c);
	}
	
	void display(){
		if (cards.size() == 0)
			System.out.println("Empty");
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(i + "-" + cards.get(i).toShortString());
		}
	}

	Card get(int i) {
		Card c = cards.get(i);
		cards.remove(c);
		return c;
	}

	int size() {
		return cards.size();
	}

}
