package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Character;

public class WaitingRoom {
	private List<Card> cards;

	public WaitingRoom() {
		super();
		cards = new ArrayList<Card>();
	}
	
	void sendToWaitingRoom(Card c){
		c.flipFaceUp();
		cards.add(c);
	}
	
	void sendToWaitingRoom(List<Card> c){
		for (Card card : c) {
			sendToWaitingRoom(card);
		}
	}

	Card salvage(int i){
		Card c = cards.get(i);
		cards.remove(i);
		return c;
	}
		
	public List<Card> refresh(){
		List<Card> newList = new ArrayList<Card>(cards);
		cards.clear();
		return newList;
	}
	
	int size(){
		return cards.size();
	}
	
	void displayWaitingRoom(){
		if (cards.size() == 0)
			System.out.println("Empty");
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(i + "-" + cards.get(i).toShortString());
		}
	}

	public void salvage(Character current) {
		cards.remove(current);
	}
	
}
