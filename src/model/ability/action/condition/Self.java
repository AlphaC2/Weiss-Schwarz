package model.ability.action.condition;

import model.card.Card;

public class Self extends Condition<Card>{

	private Card card;
	private boolean other;
	
	protected Self(Card card) {
		this(card, false);
	}
	
	protected Self(Card card, boolean other) {
		super("Self");
		this.card = card;
		this.other = other;
	}

	@Override
	public boolean check() {
		return !other && card == target;
	}

}
