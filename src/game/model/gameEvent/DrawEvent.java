package game.model.gameEvent;

import game.model.player.Player;

public class DrawEvent extends GameEvent{

	public DrawEvent(Player player) {
		super(player, EventType.DREW_CARD);
	}

}
