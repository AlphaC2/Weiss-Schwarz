package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class Stock {
	private List<Card> cards;

	public Stock() {
		super();
		cards = new ArrayList<Card>();
	}
	
	public List<Card> pay(int i){
		List<Card> result = new ArrayList<Card>();
		for (int j = 0; j < i; j++) {
			result.add(pay());
		}
		
		return result;
	}
	
	private Card pay(){
		Card c = cards.get(cards.size() - 1);
		cards.remove(c);
		return c;
	}
	
	public void addStock(Card c){
		cards.add(c);
	}
	
	public void addToBottom(Card c){
		cards.add(0, c);
	}
	
	public int getStock(){
		return cards.size();
	}
	
}
