package game.command;

import game.controller.PlayerController;
import game.model.ability.action.DrawToHand;
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
			
			for (int i = 0; i < 2; i++) {
				new DrawToHand().execute(p1, p2);
			}
		}
	}

}
