package model.ability.auto;

import controller.PlayerController;
import model.ability.Ability;
import model.card.Card;
import model.gameEvent.EventType;

public abstract class AutoAbility extends Ability{

	private EventType trigger;
	private boolean self;
	private int primed;
	private boolean optional;
	
	AutoAbility(Card source, EventType trigger, boolean self) {
		super(source);
		this.trigger = trigger;
		this.self = self;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	public boolean isSelf(){
		return self;
	}

	public boolean isResolved() {
		return primed == 0;
	}
	
	public void prime(){
		primed++;
	}
	
	@Override
	public void execute(PlayerController p1, PlayerController p2){
		if (primed > 0){
			if (optional){
				if (p1.getChoice("Activate this ability?")){
					super.execute(p1, p2);
				}
			} else {
				super.execute(p1, p2);
			}
			primed--;
		}
	}

	public EventType getTrigger() {
		return trigger;
	}

}
