package model.ability.action;

import java.util.List;

import controller.PlayerController;
import model.ability.AbilityInterface;
import model.board.ResolutionZone;
import model.card.Card;
import model.card.Climax;
import model.gameEvent.DamageCancelledEvent;
import model.gameEvent.TakeDamageEvent;

public class TakeDamage extends Action<ResolutionZone>{

	private int amount = 1;
	private AbilityInterface head;
	
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
		head = new DrawDamageToResolution();
		for (int i = 1; i < amount; i++) {
			head.last().setNextAction(new DrawDamageToResolution());
		}
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		head.execute(p1, p2);
		ResolutionZone zone = targets.get(0);
		List<Card> cards = zone.getCards();
		Card lastCard = cards.get(cards.size()-1);
		if (lastCard instanceof Climax){
			Climax climax = (Climax) lastCard;
			p1.log("Damage cancelled on card " + climax);
			p1.getBoard().getWaitingRoom().add(cards);
			zone.remove(cards);
			p1.addEvent(new DamageCancelledEvent(p1.getPlayer(), climax));
		} else {
			for (int i = 0; i <  cards.size(); i++) {
				new TakeOneDamage().execute(p1, p2);;
			}
			if(p1.isAlive()){
				p1.log("took " + amount + " damage");
			}
			p1.addEvent(new TakeDamageEvent(p1.getPlayer(), amount));
		}
	}

}
