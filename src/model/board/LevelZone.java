package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Colour;

public class LevelZone {
	List<Card> levelzone;
	
	LevelZone() {
		super();
		levelzone = new ArrayList<Card>();
	}

	boolean hasColour(Colour colour){
		for (Card card : levelzone) {
			if (card.getColour() == colour && card.isFaceUp())
				return true;
		}
		return false;
	}
	
	boolean hasCardByName(String name){
		for (Card card : levelzone) {
			if (card.getName().equals(name) && card.isFaceUp())
				return true;
		}
		return false;
	}
	
	int totalLevel(){
		int total = 0;
		for (Card card : levelzone) {
			if(card.isFaceUp())
				total += card.getLevel();
		}
		return total;
	}
	
	int level(){
		return levelzone.size();
	}
	
	void levelUp(Card c){
		c.flipFaceUp();
		levelzone.add(c);
	}

	public void display() {
		if (levelzone.size() == 0)
			System.out.println("Empty");
		for (Card card : levelzone) {
			System.out.println(card.toShortString());
		}
	}
	
}
