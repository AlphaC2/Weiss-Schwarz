package model.ability.action.condition;

import model.card.Card;

public class IsFaceUp extends Condition<Card> {

	private boolean faceup;

	public IsFaceUp(boolean faceup) {
		super("Face up");
		this.faceup = faceup;
	}

	public IsFaceUp() {
		this(true);
	}

	@Override
	public boolean check() {
		return faceup ^ target.isFaceUp();
	}

}
