package game.model.ability.action;

import java.util.ArrayList;
import java.util.List;

import game.controller.PlayerController;
import game.model.board.ResolutionZone;
import game.model.card.Card;
import game.model.card.Climax;
import game.model.gameEvent.DamageCancelledEvent;
import game.model.gameEvent.GameEvent;
import game.model.gameEvent.TakeDamageEvent;

public class TakeDamage extends Action<ResolutionZone>{

	private int amount = 1;
	
	public TakeDamage(int amount) {
		super("Take Damange");
		this.amount = amount;
	}

	@Override
	public String failureMessage() {
		return "You lost";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getResolutionZone());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		for (int i = 0; i < amount; i++) {
			new DrawToResolution().execute(p1, p2);
			if (p1.getBoard().getResolutionZone().getCardsOfType(Climax.class).size() > 0){
				break;
			}
		}
		ResolutionZone zone = targets.get(0);
		List<Card> cards = zone.getCards();
		Card lastCard = cards.get(cards.size()-1);
		if (lastCard instanceof Climax){
			Climax climax = (Climax) lastCard;
			p1.getBoard().getWaitingRoom().add(cards);
			zone.remove(cards);
			List<GameEvent> events = new ArrayList<>();
			events.add(new DamageCancelledEvent(p1.getPlayer(), climax));
			p1.addEvents(events,p2);
		} else {
			for (int i = 0; i <  cards.size(); i++) {
				new TakeOneDamage().execute(p1, p2);;
			}
			List<GameEvent> events = new ArrayList<>();
			events.add(new TakeDamageEvent(p1.getPlayer(), amount));
			p1.addEvents(events,p2);
		}
	}

}
