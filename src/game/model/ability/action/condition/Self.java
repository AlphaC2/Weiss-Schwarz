package game.model.ability.action.condition;

import game.model.card.Card;

public class Self extends Condition<Card>{

	private Card card;
	private boolean other;
	
	public Self(Card card) {
		this(card, false);
	}
	
	public Self(Card card, boolean other) {
		super("Self");
		this.card = card;
		this.other = other;
	}

	@Override
	public boolean check() {
		return (other) == (card != target);
	}

}
