package game.model.gameEvent;

import game.model.player.Player;
import game.model.board.Slot;
import game.model.card.Character;

public class StageToWaitingRoomEvent extends GameEvent{
	private Character character;
	private Slot slot;
	
	public StageToWaitingRoomEvent(Player player, Character c, Slot slot) {
		super(player, EventType.STAGE_TO_WAITINGROOM);
		this.character = c;
		this.slot = slot;
	}

	public Character getCharacter() {
		return character;
	}
	
	public Slot getSlot() {
		return slot;
	}

	@Override
	public String toString(){
		return character.toShortString() + " was sent to " + getSourcePlayer().getName() + "'s waiting room" + System.lineSeparator();
	}
}
