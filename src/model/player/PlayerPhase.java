package model.player;

public enum PlayerPhase {
	OPPONENTS_TURN, STAND, DRAW, CLOCK, MAIN, CLIMAX, ATTACK, 
	ATTACK_DECLARATION, TRIGGER, COUNTER, DAMAGE, END_OF_ATTACK, 
	ENCORE, END;

	public static PlayerPhase nextAttackStep(PlayerPhase phase) {
		if (phase == END_OF_ATTACK) {
			return ATTACK_DECLARATION;
		}
		if (phase.compareTo(ATTACK) >= 0 && phase.compareTo(DAMAGE) <= 0) {
			return values()[phase.ordinal() + 1];
		}
		throw new IllegalArgumentException(phase.toString());
	}
	
	public static PlayerPhase nextPhase(PlayerPhase phase){
		if (phase == END) {
			return OPPONENTS_TURN;
		}
		if (phase == ATTACK) {
			return ENCORE;
		}
		if ((phase.compareTo(OPPONENTS_TURN) >= 0 && phase.compareTo(CLIMAX) <= 0) 
				|| (phase == ENCORE)){
			return values()[phase.ordinal() + 1];
		}
		throw new IllegalArgumentException(phase.toString());
	}
}
