package game.model.gameEvent;

import game.model.player.Player;
import game.model.card.Character;

public class StageToWaitingRoomEvent extends GameEvent{
	private Character character;
	
	public StageToWaitingRoomEvent(Player player, Character c) {
		super(player, EventType.STAGE_TO_WAITINGROOM);
		this.character = c;
	}

	public Character getCharacter() {
		return character;
	}
	
	@Override
	public String toString(){
		return character.toShortString() + " was sent to " + getSourcePlayer().getName() + "'s waiting room" + System.lineSeparator();
	}
}
