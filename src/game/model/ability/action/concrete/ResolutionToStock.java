package game.model.ability.action.concrete;

import game.controller.PlayerController;
import game.model.ability.action.TargetedAction;
import game.model.card.Card;

public class ResolutionToStock extends TargetedAction<Card> {

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
		for (Card card : targets) {
			p1.getBoard().getStock().add(card);
			p1.getBoard().getResolutionZone().remove(card);
		}
		
		
	}

}
