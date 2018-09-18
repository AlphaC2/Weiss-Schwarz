package model.ability.condition;

import model.card.Card;
import model.card.CardType;
import model.card.Character;
import model.card.Climax;
import model.card.Event;

public class MatchesCardType extends Condition<Card>{
	private CardType type;
	
	public MatchesCardType(CardType type) {
		super("Matches " + type);
		this.type = type;
	}

	@Override
	public boolean check() {
		switch(type){
		case CLIMAX: return target instanceof Climax; 
		case CHARACTER: return target instanceof Character; 
		case EVENT: return target instanceof Event;
		}
		return false;
	}

}
