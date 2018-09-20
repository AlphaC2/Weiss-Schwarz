package model.player;

public enum PlayerPhase {
	STAND, DRAW, CLOCK, MAIN, CLIMAX, ATTACK, 
	ATTACK_DECLARATION, TRIGGER, COUNTER, DAMAGE, END_OF_ATTACK, 
	ENCORE, END, OPPONENTS_TURN;

	public static PlayerPhase nextAttackStep(PlayerPhase phase) {
		if (phase == END_OF_ATTACK) {
			return ATTACK_DECLARATION;
		}
		if (phase == ATTACK) {
			return ENCORE;
		}
		if (phase == OPPONENTS_TURN) {
			return STAND;
		}
		if ((phase.compareTo(STAND) >= 0 && phase.compareTo(CLIMAX) <= 0) 
				|| (phase == ENCORE || phase == END)){
			return values()[phase.ordinal() + 1];
		}
		throw new IllegalArgumentException(phase.toString());
	}
	
	public static PlayerPhase nextPhase(PlayerPhase phase){
		if (phase == OPPONENTS_TURN) {
			return STAND;
		}
		if (phase == ATTACK) {
			return ENCORE;
		}
		if (phase.compareTo(STAND) >= 0 && phase.compareTo(CLIMAX) <= 0) {
			return values()[phase.ordinal() + 1];
		}
		throw new IllegalArgumentException(phase.toString());
	}
}
