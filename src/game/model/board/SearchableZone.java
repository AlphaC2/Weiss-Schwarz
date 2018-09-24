package game.model.board;

import java.util.ArrayList;
import java.util.List;

import game.model.card.Card;

public abstract class SearchableZone extends Zone implements Searchable{

	public SearchableZone(String name,boolean visible) {
		super(name,visible);
	}
	
	public void remove(Card c) {
		if (!cards.remove(c)){
			throw new IllegalArgumentException("Card not in " + getName() +System.lineSeparator() + c);
		}
	}
	
	public void remove(List<Card> cards){
		for (Card card : cards) {
			remove(card);
		}
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

}
