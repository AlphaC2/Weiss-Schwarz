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
	
	AutoAbility(Card source, EventType trigger, boolean self, boolean optional) {
		super(source);
		this.trigger = trigger;
		this.self = self;
		this.optional = optional;
	}

	AutoAbility(Card source, EventType trigger, boolean self) {
		this(source,trigger, self, false);
	}

	public boolean isOptional() {
		return optional;
	}
	
	public void setOptional(boolean optional){
		this.optional = optional;
	}
	
	public int getPrimed() {
		return primed;
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
			primed--;
			if (optional){
				if (p1.getChoice("Activate this ability?")){
					super.execute(p1, p2);
				}
			} else {
				super.execute(p1, p2);
			}
		}
	}

	public EventType getTrigger() {
		return trigger;
	}

}
