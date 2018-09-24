package game.model.ability.action;

import game.controller.PlayerController;
import game.model.card.Card;
import game.model.exceptions.EmptyLibraryException;

public class DrawToResolution extends Action<Card> {

	public DrawToResolution() {
		super("Put card from library to resolution zone");
	}

	@Override
	public String failureMessage() {
		return null;
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		Card card = p1.getBoard().getLibrary().peek();
		targets.add(card);
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Card card = targets.get(0);
		p1.getBoard().getResolutionZone().add(card);
		try {
			p1.getBoard().getLibrary().remove(card);
		} catch (EmptyLibraryException e) {
			new Refresh().execute(p1, p2);
		}
		System.out.println("Resolution size " +p1.getBoard().getResolutionZone().size());
	}
}
