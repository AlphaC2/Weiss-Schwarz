package model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.card.Card;

public class WaitingRoom {
	private List<Card> cards;

	public WaitingRoom() {
		super();
		cards = new ArrayList<Card>();
	}
	
	public void sendToWaitingRoom(Card c){
		c.flipFaceUp();
		cards.add(c);
	}
	
	public Card salvage(String name){
		Card card;
		Iterator<Card> cardIterator = cards.iterator();
		
		while(cardIterator.hasNext()){
			card = cardIterator.next();
			if (card.getName().equals(name)){
				cardIterator.remove();
				return card;
			}
		}
		
		return null;
	}
	
	public List<Card> refresh(){
		List<Card> newList = new ArrayList<Card>(cards);
		cards.clear();
		return newList;
	}
	
	public int size(){
		return cards.size();
	}
	
	public void displayWaitingRoom(){
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(i + "-" + cards.get(i).toShortString());
		}
	}
	
}
