package model.ability.auto;

import model.ability.Ability;
import model.card.Card;
import model.gameEvent.EventType;
import model.gameEvent.GameEvent;

public abstract class AutoAbility extends Ability{

	private EventType trigger;
	private boolean self;
	
	AutoAbility(Card source, EventType trigger, boolean self) {
		super(source);
		this.trigger = trigger;
		this.self = self;
	}
	
	public boolean isSelf(){
		return self;
	}
	
	public boolean canActivate(GameEvent e){
		
		if (e.getType() != trigger){
			return false;
		}
		
		return true;
	}

}
