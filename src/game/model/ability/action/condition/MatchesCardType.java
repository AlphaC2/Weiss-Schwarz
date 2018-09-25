package game.model.ability.action.condition;

import game.model.card.Card;
import game.model.card.CardType;
import game.model.card.Character;
import game.model.card.Climax;
import game.model.card.Event;

public class MatchesCardType extends Condition<Card>{
	private CardType type;
	private boolean matches;
	
	public MatchesCardType(CardType type) {
		this(type, true);
	}
	
	public MatchesCardType(CardType type, boolean matches) {
		super("Doesn't matche " + type);
		this.type = type;
		this.matches = matches;
	}

	@Override
	public boolean check() {
		switch(type){
			case CLIMAX: return (target instanceof Climax ^ !matches); 
			case CHARACTER: return target instanceof Character ^ !matches; 
			case EVENT: return target instanceof Event ^ !matches;
		}
		return false;
	}

}
