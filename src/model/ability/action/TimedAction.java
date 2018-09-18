package model.ability.action;

import model.player.PhaseTiming;

public abstract class TimedAction<T> extends Action<T>{
	
	private PhaseTiming timing;

	protected TimedAction(String name, PhaseTiming timing) {
		super(name);
		this.timing = timing;
	}
	
	public PhaseTiming getTiming(){
		return timing;
	}
}
