package command;

import controller.PlayerController;
import model.card.Card;

public class Draw extends Command{

	public Draw( ) {
		super("Draw");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		Card c = p1.getPlayer().getBoard().draw();
		p1.log(p1.getPlayer().getName() + " drew " + System.lineSeparator() +c.toShortString());
		p1.getPlayer().endPhase();
	}

}
