package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class Stock {
	private List<Card> cards;

	Stock() {
		super();
		cards = new ArrayList<Card>();
	}
	
	List<Card> pay(int i){
		List<Card> result = new ArrayList<Card>();
		for (int j = 0; j < i; j++) {
			result.add(pay());
		}
		
		return result;
	}
	
	Card pay(){
		Card c = cards.get(cards.size() - 1);
		cards.remove(c);
		return c;
	}
	
	void addStock(Card c){
		cards.add(c);
	}
	
	void addToBottom(Card c){
		cards.add(0, c);
	}
	
	int getStock(){
		return cards.size();
	}
	
}
