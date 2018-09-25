package game.model.ability.auto;

import game.model.card.Card;
import game.model.gameEvent.EventType;
import game.model.gameEvent.GameEvent;

public class DummyAutoAbility extends AutoAbility {

	public DummyAutoAbility(Card source, EventType trigger, boolean self) {
		super(source, trigger, self);
	}
	
	public DummyAutoAbility(Card source, EventType trigger, boolean self, boolean optional) {
		super(source, trigger, self, optional);
	}

	@Override
	protected boolean checkPrime(GameEvent e) {
		return true;
	}
	
}
