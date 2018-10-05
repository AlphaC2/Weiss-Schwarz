package game.model.ability.auto;

import game.model.card.Card;
import game.model.player.PhaseTiming;
import game.model.player.PlayerPhase;

public class DummyPhaseAutoAbility extends PhaseAutoAbility{

	DummyPhaseAutoAbility(Card source, boolean self, PlayerPhase phase, PhaseTiming timing) {
		super(source, self, phase, timing);
	}
	
	DummyPhaseAutoAbility(Card source, boolean self, PlayerPhase phase, PhaseTiming timing, boolean isOptional) {
		super(source, self, phase, timing, isOptional);
	}

}
