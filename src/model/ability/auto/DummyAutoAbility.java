package model.ability.auto;

import model.ability.action.PlaceInDamageFromLibrary;
import model.card.Card;
import model.gameEvent.EventType;

public class DummyAutoAbility extends AutoAbility {

	public DummyAutoAbility(Card source, EventType trigger, boolean self) {
		super(source, trigger, self);
		addAction(new PlaceInDamageFromLibrary());
	}
	
	

}
