package model.ability.action;

import controller.PlayerController;
import model.card.Card;
import model.exceptions.EmptyLibraryException;
import model.gameEvent.DrawEvent;

public class DrawToHand extends Action<Card>{

	public DrawToHand() {
		super("Draw to hand");
	}

	@Override
	public String failureMessage() {
		return "You lose";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getLibrary().peek());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Card c = targets.get(0);
		p1.getBoard().getHand().add(c);
		try {
			p1.getBoard().getLibrary().remove(c);
		} catch (EmptyLibraryException e) {
			new Refresh().execute(p1, p2);
		}
		p1.log(p1.getPlayer().getName() + " drew " + System.lineSeparator() +targets.get(0).toShortString());
		p1.addEvent(new DrawEvent(p1.getPlayer()));
	}

}
