package model.ability.action;

import controller.PlayerController;
import model.card.Card;
import model.exceptions.EmptyLibraryException;

public class DrawToHand extends Action<Card>{

	public DrawToHand() {
		super("Draw to hand");
	}

	@Override
	public String failureMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) throws EmptyLibraryException {
		p1.getBoard().getLibrary().draw();
		
	}

}
