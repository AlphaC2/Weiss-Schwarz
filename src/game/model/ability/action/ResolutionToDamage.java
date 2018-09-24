package game.model.ability.action;

import game.controller.PlayerController;
import game.model.board.DamageZone;
import game.model.board.ResolutionZone;
import game.model.card.Card;

public class ResolutionToDamage extends Action<ResolutionZone> {

	ResolutionToDamage() {
		super("Move cards from Resolution To Damage");
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
		Card c;

		while (zone.getCards().size() > 0) {
			c = zone.getCards().get(0);
			zone.remove(c);
			p1.getBoard().getDamageZone().add(c);
			p1.log("Damage:" + c.toShortString());
			if (p1.getBoard().getDamageZone().size() >= DamageZone.cardsPerLevel) {
				new LevelUp().execute(p1, p2);
			}
		}

	}

}
