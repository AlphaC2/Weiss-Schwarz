package model.ability.action.condition;

import model.card.Card;

public class Self extends Condition<Card>{

	private Card card;
	
	protected Self(Card card) {
		super("Self");
		this.card = card;
	}

	@Override
	public boolean check() {
		return card.equals(target);
	}

}
