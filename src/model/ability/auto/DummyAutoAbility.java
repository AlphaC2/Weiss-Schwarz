package model.ability.auto;

import model.card.Card;
import model.gameEvent.EventType;

public class DummyAutoAbility extends AutoAbility {

	public DummyAutoAbility(Card source, EventType trigger, boolean self) {
		super(source, trigger, self);
	}
	
	public DummyAutoAbility(Card source, EventType trigger, boolean self, boolean optional) {
		super(source, trigger, self, optional);
	}
	
}
