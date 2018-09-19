package model.ability.auto;

import model.card.Card;
import model.gameEvent.EventType;
import model.player.PhaseTiming;
import model.player.PlayerPhase;

public class DummyPhaseAutoAbility extends PhaseAutoAbility{

	DummyPhaseAutoAbility(Card source, boolean self, PlayerPhase phase, PhaseTiming timing) {
		super(source, self, phase, timing);
	}
	
	DummyPhaseAutoAbility(Card source, boolean self, PlayerPhase phase, PhaseTiming timing, boolean isOptional) {
		super(source, self, phase, timing, isOptional);
	}
	

}
