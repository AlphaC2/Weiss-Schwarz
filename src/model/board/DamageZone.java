package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class DamageZone {
	private List<Card> damage;
	
	public DamageZone() {
		super();
		damage = new ArrayList<Card>();
	}	
	
	public void takeDamage(Card c){
		damage.add(c);
	}
	
	public Card getTop(){
		if (damage.size() > 0){
			return damage.get(damage.size());
		}else
			return null;
	}
	
	public Card heal(){
		if (damage.size() > 0){
			Card top = damage.get(damage.size());
			damage.remove(top);
			return top;
		}else
			return null;
	}
	
	public Card getLevelCard(int i){
		Card c = damage.remove(i);
		return c;
	}
	
	public List<Card> levelUpWaitingRoom(){
		List<Card> result = new ArrayList<Card>(damage);
		damage.clear();
		return result;
	}
	
	public void display(){
		for (Card card : damage) {
			System.out.println(card.toShortString());
		}
	}

	public void takeRefreshDamage(Card refresh) {
		damage.add(refresh);
	}
	
}
