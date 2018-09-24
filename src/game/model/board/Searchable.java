package game.model.board;

import java.util.List;

import game.model.card.Card;

public interface Searchable {

	boolean search(String name);
	
	<T extends Card> List<T> getCardsOfType(Class<T> type);
	
}
