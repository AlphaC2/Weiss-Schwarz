package model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.card.Card;
import model.exceptions.CardNotInZoneException;

public class MemoryZone {
	private List<Card> cards;
	
	public MemoryZone(){
		cards = new ArrayList<Card>();
	}
	
	public void sendToMemory(Card c){
		cards.add(c);
	}
	
	public Card retrieveFromMemory(String name){
		Iterator<Card> cardIterator = cards.iterator();
		Card card;
		while(cardIterator.hasNext()){
			card = cardIterator.next();
			if(card.getName().equals(name)){
				cardIterator.remove();
				return card;
			}
		}
	
		return null;
	}

	public Card retrieveFromMemory(Card c) throws CardNotInZoneException{
		if(cards.contains(c)){
			cards.remove(c);
			return c;
		}else {
			throw new CardNotInZoneException();
		}
		
	}
	
	public int size(){
		return cards.size();
	}
	
	public boolean contains(String name){
		for (Card card : cards) {
			if (card.getName().equals(name))
				return true;
		}
		return false;
	}
	
	
}
