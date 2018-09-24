package game.model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.model.card.Card;
import game.model.exceptions.EmptyLibraryException;

public class Library extends Zone implements Searchable{
	
	Library(){
		super("Library", false);
	}

	Library(List<Card> cards) {
		this();
		super.add(cards);
		for (Card card : this.cards) {
			card.flipFaceDown();
		}
	}
	
	public Card peek(){
		return cards.get(0);
	}
	
	private void after() throws EmptyLibraryException{
		if (cards.size() == 0){
			throw new EmptyLibraryException();
		}
	}
	
	public Card draw() throws EmptyLibraryException{
		Card c = cards.get(0);
		cards.remove(c);
		after();
		return c;
	}
	
	public void remove(Card c) throws EmptyLibraryException{
		if (!cards.remove(c)){
			throw new IllegalArgumentException("Card not in " + getName() +System.lineSeparator() + c);
		}
		after();
	}
	
	@Override
	public boolean search(String name) {
		for (Card card : cards) {
			if (card.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Card> List<T> getCardsOfType(Class<T> type) {
		List<T> results = new ArrayList<>();
		for (Card card : cards) {
			if (card.getClass().equals(type)){
				results.add((T) card);
			}
		}
		return results;
	}
	
	public void placeTop(Card c){
		c.flipFaceDown();
		cards.add(0, c);
	}
	
	public void placeBottom(Card c){
		super.add(c);
	}
	
	public void shuffle(){
		Collections.shuffle(cards);
	}

	@Override
	public Library newInstance() {
		return new Library();
	}


}
