package game.model.ability.action.concrete;

import game.model.ability.action.TargetedAction;
import game.model.player.PhaseTiming;

public abstract class TimedAction<T> extends TargetedAction<T>{
	
	private PhaseTiming timing;

	protected TimedAction(String name, PhaseTiming timing) {
		super(name);
		this.timing = timing;
	}
	
	protected TimedAction(String name, boolean required, PhaseTiming timing){
		super(name, required);
		this.timing = timing;
	}
	
	public PhaseTiming getTiming(){
		return timing;
	}
}
