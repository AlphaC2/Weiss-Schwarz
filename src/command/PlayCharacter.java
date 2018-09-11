package command;

import controller.PlayerController;
import model.ability.action.PlayCard;

public class PlayCharacter extends Command{

	public PlayCharacter() {
		super("Play Card");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) throws Exception{
		PlayCard playCard = new PlayCard();
		playCard.execute(p1, p2);
	}

}
