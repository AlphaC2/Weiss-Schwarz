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
	
	List<Card> takeDamage(List<Card> cards){
		for (Card card : cards) {
			damage.add(card);			
		}
		return levelUp();
	}
	
	Card getBottom(){
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
	
	void display(){
		if (damage.size() == 0)
			System.out.println("Empty");
		for (Card card : damage) {
			System.out.println(damage.indexOf(card) + " - " + card.toShortString());
		}
	}
	
	boolean hasColour(Colour colour){
		for (Card card : damage) {
			if(card.getColour() == colour && card.isFaceUp())
				return true;
		}
		return false;
	}

	public void takeNoCancelDamage(Card card) {
		damage.add(card);
	}

	public int size() {
		return damage.size();
	}

}
