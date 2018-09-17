package model.gameEvent;

import model.player.Player;

public class DrawEvent extends GameEvent{

	public DrawEvent(Player player) {
		super(player, EventType.DREW_CARD);
	}

}
