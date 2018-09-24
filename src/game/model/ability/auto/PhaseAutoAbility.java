package game.model.ability.auto;

import game.model.card.Card;
import game.model.gameEvent.EventType;
import game.model.player.PhaseTiming;
import game.model.player.PlayerPhase;

public abstract class PhaseAutoAbility extends AutoAbility{

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
	
	

}
