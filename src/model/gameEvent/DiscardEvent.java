package model.gameEvent;

import model.card.Card;
import model.player.Player;

public class DiscardEvent extends GameEvent {

	private Card card;
	
	public DiscardEvent(Player player, Card card) {
		super(player, EventType.DISCARD_CARD);
		this.card = card;
	}
	
	public Card getCard(){
		return card;
	}
	
	@Override
	public String toString(){
		return getSourcePlayer().getName() + " discarded " + System.lineSeparator() + card.toShortString();
	}

}
