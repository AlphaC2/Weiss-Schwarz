package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class LevelZone {
	List<Card> levelzone;
	
	public LevelZone() {
		super();
		levelzone = new ArrayList<Card>();
	}

	public boolean hasColour(String colour){
		for (Card card : levelzone) {
			if (card.getColour().equals(colour) && card.isFaceUp())
				return true;
		}
		return false;
	}
	
	public boolean hasCardByName(String name){
		for (Card card : levelzone) {
			if (card.getName().equals(name) && card.isFaceUp())
				return true;
		}
		return false;
	}
	
	public int totalLevel(){
		int total = 0;
		for (Card card : levelzone) {
			if(card.isFaceUp())
				total += card.getLevel();
		}
		return total;
	}
	
	public int level(){
		return levelzone.size();
	}
	
	public void levelUp(Card c){
		c.flipFaceUp();
		levelzone.add(c);
	}
	
	
	
}
