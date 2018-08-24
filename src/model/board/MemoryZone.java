package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class MemoryZone {
	private List<Card> cards;
	
	MemoryZone(){
		cards = new ArrayList<Card>();
	}
	
	void sendToMemory(Card c){
		cards.add(c);
	}
	
	Card retrieveFromMemory(int i){
		Card c = cards.get(i);
		cards.remove(i);
		return c;
	}
	
	int size(){
		return cards.size();
	}
	
	boolean contains(String name){
		for (Card card : cards) {
			if (card.getName().equals(name))
				return true;
		}
		return false;
	}
	
}
