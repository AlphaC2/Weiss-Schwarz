package command;

import controller.PlayerController;
import model.card.Card;

public class Discard extends Command{

	public Discard( ) {
		super("Discard");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayHand();
		Card card = p1.getChoice("Choose card to discard", p1.getBoard().getHand().getCards());
		p1.getBoard().getHand().remove(card);
		p1.getBoard().getWaitingRoom().add(card);
	}

}
