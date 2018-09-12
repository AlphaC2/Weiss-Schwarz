package model.ability.action;

import controller.PlayerController;
import model.board.DamageZone;
import model.board.ResolutionZone;
import model.card.Card;

public class TakeOneDamage extends Action<ResolutionZone> {

	TakeOneDamage() {
		super("Take 1 dmg");
	}

	@Override
	public String failureMessage() {
		return null;
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getResolutionZone());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		ResolutionZone zone = targets.get(0);
		Card c = zone.getCards().get(0);
		zone.remove(c);
		p1.getBoard().getDamageZone().add(c);
		p1.log("Damage:" + c.toShortString());
		if (p1.getBoard().getDamageZone().size() >= DamageZone.cardsPerLevel) {
			new LevelUp().execute(p1, p2);
		}

	}

}
