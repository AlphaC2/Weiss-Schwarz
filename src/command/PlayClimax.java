package command;

import controller.PlayerController;
import model.card.Card;
import model.card.Climax;

public class PlayClimax extends Command{

	public PlayClimax() {
		super("Play Climax");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayHand();
		boolean choice = p1.getChoice("Play a Climax");
		if (choice) {
			Card card = p1.chooseCardFromHand();
			if (card instanceof Climax) {
				p1.getPlayer().getBoard().playClimax((Climax) card);
				return;
			} else {
				p1.log("Not a climax");
			}
		}
	}

}
