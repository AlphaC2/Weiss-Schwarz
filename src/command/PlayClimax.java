package command;

import controller.PlayerController;
import model.card.Card;
import model.card.Climax;

public class PlayClimax extends Command{

	public PlayClimax() {
		super("Play Climax");
	}
	
	private boolean checkCondition(PlayerController controller){
		return controller.getPlayer().getHand().getCardsOfType(Climax.class).size() > 0;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		if (!checkCondition(p1)){
			p1.log("No climax to play");
		} else {
			p1.displayHand();
			boolean choice = p1.getChoice("Play a Climax");
			if (choice) {
				Card card = p1.chooseCardFromHand(Climax.class);
				if (card instanceof Climax) {
					p1.getPlayer().getBoard().playClimax((Climax) card);
				} else {
					p1.log("Not a climax");
				}
			}
		}
		p1.getPlayer().endPhase();
	}

}
