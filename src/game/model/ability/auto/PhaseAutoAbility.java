package game.model.ability.auto;

import game.model.card.Card;
import game.model.gameEvent.EventType;
import game.model.gameEvent.GameEvent;
import game.model.gameEvent.PhaseEvent;
import game.model.player.PhaseTiming;
import game.model.player.PlayerPhase;

public abstract class PhaseAutoAbility extends AutoAbility {

	private PhaseTiming timing;
	private PlayerPhase phase;

	PhaseAutoAbility(Card source, boolean self, PlayerPhase phase, PhaseTiming timing) {
		super(source, EventType.PHASE, self);
		this.phase = phase;
		this.timing = timing;
	}

	PhaseAutoAbility(Card source, boolean self, PlayerPhase phase, PhaseTiming timing, boolean isOptional) {
		super(source, EventType.PHASE, self, isOptional);
		this.phase = phase;
		this.timing = timing;
	}

	public PhaseTiming getTiming() {
		return timing;
	}

	public PlayerPhase getPhase() {
		return phase;
	}

	@Override
	protected boolean checkPrime(GameEvent e) {
		if (e instanceof PhaseEvent){
			PhaseEvent pe = (PhaseEvent) e;
			return getPhase() == pe.getPt().getPhase() && getTiming() == pe.getPt().getTiming();
		}
		return false;
	}
	
	
	
}


