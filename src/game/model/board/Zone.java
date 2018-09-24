package game.model.board;

import java.util.ArrayList;
import java.util.List;

import game.model.card.Card;

public abstract class Zone {
	protected List<Card> cards;
	private String name;
	private boolean visible;
	
	public Zone(String name){
		this(name,false);
	}
	
	public Zone(String name, boolean visible){
		this.name = name;
		this.visible = visible;
		cards = new ArrayList<Card>();
	}
	
	public void add(Card c){
		if(visible){
			c.flipFaceUp();
		} else {
			c.flipFaceDown();
		}
		cards.add(c);
	}
	
	public void add(List<Card> cards){
		for (Card card : cards) {
			add(card);
		}
	}
	
	public int size(){
		return cards.size();
	}
	
	public List<Card> getCards() {
		return new ArrayList<>(cards);
	}
	
	protected String getName(){
		return name;
	}
	
	@Override
	public String toString() {
		if (cards.size() == 0)
			return name + " is empty";
		
		String s = name + " has " + cards.size() + " cards" + System.lineSeparator();
		if(!visible){
			return s;
		}
		
		for (Card card : cards) {
			s += card.toShortString() + System.lineSeparator();
		}
		return s;
	}
}
