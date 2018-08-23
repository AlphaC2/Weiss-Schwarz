package model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.card.Card;
import model.exceptions.CardNotInZoneException;

public class Hand {
	private List<Card> cards;
	
	public Hand() {
		super();
		cards = new ArrayList<Card>();
	}

	public void add(Card c){
		cards.add(c);
	}
	
	public Card get(String name) throws CardNotInZoneException{
		Card card;
		Iterator<Card> cardIterator = cards.iterator();
		
		while(cardIterator.hasNext()){
			card = cardIterator.next();
			if (card.getName().equals(name)){
				cardIterator.remove();
				return card;
			}
		}
		
		throw new CardNotInZoneException();
	}
	
	public void display(){
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(i + "-" + cards.get(i).toShortString());
		}
	}

	public Card get(int i) {
		Card c = cards.get(i);
		cards.remove(c);
		return c;
	}

	public int size() {
		return cards.size();
	}

}
