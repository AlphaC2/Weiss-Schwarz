package game.model.board;

import java.util.ArrayList;
import java.util.List;

import game.model.card.Card;

public class WaitingRoom extends SearchableZone{

	public WaitingRoom() {
		super("Waiting Room", true);
	}
	
	public List<Card> refresh(){
		List<Card> newList = new ArrayList<Card>(cards);
		cards.clear();
		return newList;
	}
}
