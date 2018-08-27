package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Colour;

public class DamageZone {
	private List<Card> damage;
	
	DamageZone() {
		super();
		damage = new ArrayList<Card>();
	}	
	
	void takeDamage(Card c){
		damage.add(c);
	}
	
	Card getTop(){
		if (damage.size() > 0){
			return damage.get(damage.size());
		}else
			return null;
	}
	
	Card heal(){
		if (damage.size() > 0){
			Card top = damage.get(damage.size());
			damage.remove(top);
			return top;
		}else
			return null;
	}
	
	Card getLevelCard(int i){
		Card c = damage.remove(i);
		return c;
	}
	
	List<Card> levelUp(){
		List<Card> result = new ArrayList<Card>(damage);
		damage.clear();
		return result;
	}
	
	void display(){
		for (Card card : damage) {
			System.out.println(card.toShortString());
		}
	}
	
	boolean hasColour(Colour colour){
		for (Card card : damage) {
			if(card.getColour() == colour && card.isFaceUp())
				return true;
		}
		return false;
	}

}
