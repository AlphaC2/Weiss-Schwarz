package model.ability.action;

import controller.PlayerController;
import model.card.Card;

public class Refresh extends Action<Card>{

	public Refresh() {
		super("Refresh");
		setNextAction(new ShuffleLibrary());
	}

	@Override
	public String failureMessage() {
		return "You lost";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.addAll(p1.getBoard().getWaitingRoom().getCards());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2){
		for (Card card : targets) {
			p1.getBoard().getWaitingRoom().remove(card);
			p1.getBoard().getLibrary().add(card);
		}
	}

}
