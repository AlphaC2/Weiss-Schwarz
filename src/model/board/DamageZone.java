package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Colour;

public class DamageZone extends SearchableZone {
	private List<Card> damage;
	
	DamageZone() {
		super("Damage", true);
		damage = new ArrayList<Card>();
	}	
	
	List<Card> takeDamage(List<Card> cards){
		for (Card card : cards) {
			damage.add(card);			
		}
		return levelUp();
	}
	
	Card heal(){
		if (damage.size() > 0){
			Card top = damage.get(damage.size());
			damage.remove(top);
			return top;
		}else
			return null;
	}
	
	List<Card> levelUp(){
		if (damage.size() >= 7){
			List<Card> result = new ArrayList<Card>();
			for (int i = 0; i < 7; i++) {
				result.add(damage.remove(0));
			}
			return result;
		}
		else return null;
	}
	
	boolean hasColour(Colour colour){
		for (Card card : damage) {
			if(card.getColour() == colour && card.isFaceUp())
				return true;
		}
		return false;
	}
	
}
