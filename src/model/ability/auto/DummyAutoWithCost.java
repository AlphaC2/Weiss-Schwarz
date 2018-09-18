package model.ability.auto;

import model.ability.action.DrawToHand;
import model.ability.action.PayStock;
import model.ability.action.Rest;
import model.ability.action.TakeDamage;
import model.card.Card;
import model.gameEvent.EventType;

public class DummyAutoWithCost extends AutoAbility {

	DummyAutoWithCost(Card source, EventType trigger, boolean self, boolean optional) {
		super(source, trigger, self, optional);
		addCost(new PayStock(2));
		addCost(new Rest());
		addAction(new TakeDamage(1));
		addAction(new DrawToHand());
		
		
	}

}
