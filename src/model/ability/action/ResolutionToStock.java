package model.ability.action;

import controller.PlayerController;
import model.card.Card;

public class ResolutionToStock extends Action<Card> {

	public ResolutionToStock() {
		super("Resolution to Stock");
	}

	@Override
	public String failureMessage() {
		return "No card in resolution";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.addAll(p1.getBoard().getResolutionZone().getCards());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		p1.getBoard().getStock().add(targets);				
	}

}
