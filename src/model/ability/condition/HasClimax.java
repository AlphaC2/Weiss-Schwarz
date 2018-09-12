package model.ability.condition;

import model.card.Card;
import model.card.Climax;

public class HasClimax extends Condition<Card>{

	public HasClimax() {
		super("Has Climax");
	}

	@Override
	public boolean check() {
		return target instanceof Climax;
	}

}
