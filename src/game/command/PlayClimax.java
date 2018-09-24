package game.command;

import java.util.List;

import game.controller.PlayerController;
import game.model.card.Card;
import game.model.card.Climax;

public class PlayClimax extends Command{

	public PlayClimax() {
		super("Play Climax");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		List<Climax> list = p1.getBoard().getHand().getCardsOfType(Climax.class);
		if (list.size() == 0){
			p1.log("No climax to play");
		} else {
			p1.displayHand();
			boolean choice = p1.getChoice("Play a Climax");
			if (choice) {
				Card card = p1.getChoice("Choose a climax to play", list);
				if (card instanceof Climax) {
					p1.getBoard().playClimax((Climax) card);
				} else {
					p1.log("Not a climax");
				}
			}
		}
	}

}
