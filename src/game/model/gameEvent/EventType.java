package game.model.gameEvent;

public enum EventType {
	//Start or end of a phase
	PHASE,
	
	//Attacking events
	DECLARE_ATTACK,
	REVERSED,
	TOOK_DAMAGE,
	CANCEL_DAMAGE,
	CHARACTER_KILLED,
	
	//Other events
	DREW_CARD,
	DISCARD_CARD,
	PLACED_ON_STAGE,
	SALVAGE,
	HEAL,
	PLAY_CLIMAX,
	ACTIVATE_BACKUP,
}
