package model.ability.action;

import controller.PlayerController;
import model.card.Card;
import model.exceptions.EmptyLibraryException;

public class PlaceInDamageFromLibrary extends Action<Card>{

	public PlaceInDamageFromLibrary() {
		super("Place damage from library");
	}

	@Override
	public String failureMessage() {
		return "empty library";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getLibrary().peek());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Card c = targets.get(0);
		try {
			p1.getBoard().getDamageZone().add(c);
			p1.getBoard().getLibrary().remove(c);
		} catch (EmptyLibraryException e) {
			new Refresh().execute(p1, p2);
		}
		
	}

}
