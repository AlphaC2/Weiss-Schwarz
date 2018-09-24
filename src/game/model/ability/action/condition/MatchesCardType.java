package game.model.ability.action.condition;

import game.model.card.Card;
import game.model.card.CardType;
import game.model.card.Character;
import game.model.card.Climax;
import game.model.card.Event;

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
