package command;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.DamageZone;
import model.card.Card;
import model.card.Climax;

public class TakeDamage extends Command {

	private int amount;

	public TakeDamage(int amount) {
		super("Take Damage");
		this.amount = amount;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		List<Card> damageCards = new ArrayList<Card>();
		Board board = p1.getBoard();
		Card c;
		for (int i = 1; i <= amount; i++) {
			c = board.getLibrary().draw();
			p1.log("Damage " + i + ":" + c.toShortString());
			damageCards.add(c);
			if (c instanceof Climax) {
				p1.log("Damage cancelled on card " + i);
				board.getWaitingRoom().add(damageCards);
				damageCards = null;
				break;
			}
		}

		String name = p1.getPlayer().getName();
		if (damageCards != null) {
			
			for (Card card : damageCards) {
				board.getDamageZone().add(card);
			}
			p1.log(name + " took " + amount + " damage");
			
			if(board.getDamageZone().size() >= DamageZone.cardsPerLevel ){
				new LevelUp().execute(p1, p2);
			}
		}
	}

}
