package command;

import java.util.List;

import controller.PlayerController;
import model.card.Card;

public class TakeDamage extends Command{

	public TakeDamage() {
		super("Take Damage");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		List<Card> cards = p1.getBoard().takeDamage(1);
		if (cards != null) {
			p1.log(p1.getPlayer().getName() + " Leveled Up");
			Card chosen = p1.getChoice(p1.getPlayer().getName() + " choose a card to level up", cards);
			p1.getBoard().levelUp(cards, chosen);
		}
	}

}
