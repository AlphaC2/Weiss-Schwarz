package game.model.ability.action;

import java.util.List;

import game.controller.PlayerController;
import game.model.board.ResolutionZone;
import game.model.card.Card;

public class ResolutionToWaitingRoom extends Action<ResolutionZone>{

	ResolutionToWaitingRoom() {
		super("Move cards from Resolution to Waiting Room");
	}

	@Override
	public String failureMessage() {
		return "Resolution Zone doesn't exist";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getResolutionZone());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		ResolutionZone zone = targets.get(0);
		List<Card> cards = zone.getCards();
		p1.getBoard().getWaitingRoom().add(cards);
		zone.remove(cards);
	}

}
