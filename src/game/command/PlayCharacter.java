package game.command;

import game.controller.PlayerController;
import game.model.ability.action.concrete.PlayCardFromHand;

public class PlayCharacter extends Command{

	public PlayCharacter() {
		super("Play Card");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		PlayCardFromHand playCard = new PlayCardFromHand();
		playCard.execute(p1, p2);
	}

}
