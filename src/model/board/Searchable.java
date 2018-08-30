package model.board;

import java.util.List;

import model.card.Card;

public interface Searchable {

	boolean search(String name);
	
	<T extends Card> List<T> getCardsOfType(Class<T> type);
	
}
