package game.model.ability.action;

import java.util.ArrayList;
import java.util.List;

import game.controller.PlayerController;
import game.model.gameEvent.GameEvent;
import game.model.gameEvent.PhaseEvent;
import game.model.player.PhaseTiming;

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
		List<GameEvent> events = new ArrayList<>();
		events.add(new PhaseEvent(p1.getPlayer(), timing));
		p1.addEvents(events,p2);
	}

}
