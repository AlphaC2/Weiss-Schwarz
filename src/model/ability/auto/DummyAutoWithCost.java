package model.ability.auto;

import model.card.Card;
import model.gameEvent.EventType;

public class DummyAutoWithCost extends AutoAbility {

	DummyAutoWithCost(Card source, EventType trigger, boolean self, boolean optional) {
		super(source, trigger, self, optional);
	}

}
