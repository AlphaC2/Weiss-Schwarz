package game.model.ability.auto;

import game.controller.PlayerController;
import game.model.ability.startup.StartUpAbility;
import game.model.card.Card;
import game.model.gameEvent.EventType;

public abstract class AutoAbility extends StartUpAbility{

	private EventType trigger;
	private int primed;
	private boolean optional;
	
	AutoAbility(Card source, EventType trigger, boolean self, boolean optional) {
		super(source,self);
		this.trigger = trigger;
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
