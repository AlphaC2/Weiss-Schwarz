package game.command;

import game.controller.PlayerController;
import game.model.ability.action.concrete.DrawToHand;
import game.model.ability.action.concrete.LevelUp;
import game.model.board.DamageZone;
import game.model.card.Card;

public class Clock extends Command{

	public Clock () {
		super("Clock");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayHand();
		boolean clock = p1.getChoice("Clock?");
		if (clock) {
			Card c = p1.getChoice("Pick card to clock", p1.getBoard().getHand().getCards());
			p1.getBoard().getHand().remove(c);
			p1.getBoard().getDamageZone().add(c);
			
			if (p1.getBoard().getDamageZone().size() >= DamageZone.cardsPerLevel) {
				new LevelUp().execute(p1, p2);
			}
			
			for (int i = 0; i < 2; i++) {
				new DrawToHand().execute(p1, p2);
			}
		}
	}

}
