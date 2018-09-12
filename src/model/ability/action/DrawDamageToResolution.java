package model.ability.action;

import controller.PlayerController;
import model.card.Card;
import model.card.Climax;
import model.exceptions.EmptyLibraryException;

public class DrawDamageToResolution extends Action<Card>{

	public DrawDamageToResolution() {
		super("Put card from library to resolution zone");
	}

	@Override
	public String failureMessage() {
		return null;
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		Card card = p1.getBoard().getLibrary().peek();
		if (card instanceof Climax){
			setNextAction(null);
		} 
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
		
	}

}
