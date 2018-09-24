package game.model.ability.auto;

import game.model.card.Card;
import game.model.gameEvent.EventType;

public class DummyAutoAbility extends AutoAbility {

	public DummyAutoAbility(Card source, EventType trigger, boolean self) {
		super(source, trigger, self);
	}
	
	public DummyAutoAbility(Card source, EventType trigger, boolean self, boolean optional) {
		super(source, trigger, self, optional);
	}
	
}
