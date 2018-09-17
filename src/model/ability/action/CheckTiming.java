package model.ability.action;

import controller.PlayerController;
import model.gameEvent.PhaseEvent;
import model.player.PhaseTiming;

public class CheckTiming extends Action<PlayerController>{
	
	private PhaseTiming timing;
	
	public CheckTiming(PhaseTiming timing) {
		super("Check Timing");
		this.timing = timing;
	}

	@Override
	public String failureMessage() {
		return null;
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1);
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		p1.addEvent(new PhaseEvent(p1.getPlayer(), timing));
	}

}
