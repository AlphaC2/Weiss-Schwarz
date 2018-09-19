package model.gameEvent;

import model.player.PhaseTiming;
import model.player.Player;
import model.player.PlayerPhase;

public class PhaseEvent extends GameEvent {

	private PlayerPhase phase;
	private PhaseTiming timing;
	
	public PhaseEvent(Player player, PhaseTiming timing) {
		super(player, EventType.PHASE);
		this.phase = player.getPhase();
		this.timing = timing;
	}

	public PlayerPhase getPhase() {
		return phase;
	}

	public PhaseTiming getTiming() {
		return timing;
	}
	
	public String toString(){
		return getSourcePlayer().getName() + " " + phase + " " + timing;
	}

}
