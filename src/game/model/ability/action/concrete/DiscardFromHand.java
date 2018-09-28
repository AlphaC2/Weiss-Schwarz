package game.model.ability.action.concrete;

import java.util.ArrayList;
import java.util.List;

import game.controller.PlayerController;
import game.model.ability.action.TargetedAction;
import game.model.card.Card;
import game.model.gameEvent.DiscardEvent;
import game.model.gameEvent.GameEvent;

public class DiscardFromHand extends TargetedAction<Card>{

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
		p1.addEvents(events,p2);
	}

}
