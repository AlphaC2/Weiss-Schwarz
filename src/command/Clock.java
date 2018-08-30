package command;

import controller.PlayerController;
import model.card.Card;

public class Clock extends Command{

	public Clock () {
		super("Clock");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayHand();
		boolean clock = p1.getChoice("Clock?");
		if (clock) {
			Card c = p1.chooseCardFromHand();
			p1.getPlayer().getBoard().clock(c);
			p1.getPlayer().getBoard().draw(2);
			return;
		}
		
	}

}
