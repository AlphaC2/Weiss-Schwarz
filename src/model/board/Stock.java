package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class Stock extends Zone{

	Stock() {
		super("Stock");
	}
	
	List<Card> pay(int i){
		if (cards.size() >= i){
			List<Card> result = new ArrayList<Card>();
			for (int j = 0; j < i; j++) {
				result.add(pay());
			}
			
			return result;
		}else
			return null;
	}
	
	private Card pay(){
		Card c = cards.get(cards.size() - 1);
		cards.remove(c);
		return c;
	}
	
}
