package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Character;

public class WaitingRoom extends SearchableZone{
	private List<Card> cards;

	public WaitingRoom() {
		super("Waiting Room", true);
		cards = new ArrayList<Card>();
	}
	
	public List<Card> refresh(){
		List<Card> newList = new ArrayList<Card>(cards);
		cards.clear();
		return newList;
	}

}
