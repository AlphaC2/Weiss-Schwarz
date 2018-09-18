package model.ability.action;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.card.Card;
import model.gameEvent.DiscardEvent;
import model.gameEvent.GameEvent;

public class DiscardFromHand extends Action<Card>{

	public DiscardFromHand(){
		super("Discard from hand");
	}
	
	public DiscardFromHand(boolean required) {
		super("Discard from hand", required);
	}

	@Override
	public String failureMessage() {
		return "hand is empty";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.addAll(p1.getBoard().getHand().getCards());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Card card = p1.getChoice("Choose card to discard", targets);
		p1.getBoard().getHand().remove(card);
		p1.getBoard().getWaitingRoom().add(card);
		List<GameEvent> events = new ArrayList<>();
		events.add(new DiscardEvent(p1.getPlayer(),card));
		p1.addEvents(events);
	}

}
