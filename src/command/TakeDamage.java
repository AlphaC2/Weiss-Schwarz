package command;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.card.Card;
import model.card.Climax;

public class TakeDamage extends Command{
	
	private int amount;
	
	public TakeDamage(int amount) {
		super("Take Damage");
		this.amount = amount;
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		//List<Card> cards = p1.getBoard().takeDamage(amount);
		List<Card> cards = new ArrayList<Card>();
		Board board = p1.getBoard();
		Card c;
		for (int i = 0; i < amount; i++) {
			c = board.getLibrary().draw();
			cards.add(c);
			if (c instanceof Climax){
				board.getWaitingRoom().add(cards);
				cards = null;
				break;
			}
		}
		if(cards != null)
			cards = board.getDamageZone().takeDamage(cards);
		
		if (cards != null) {
			p1.log(p1.getPlayer().getName() + " Leveled Up");
			Card chosen = p1.getChoice(p1.getPlayer().getName() + " choose a card to level up", cards);
			
			board.getLevel().add(chosen);
			cards.remove(chosen);
			board.getWaitingRoom().add(cards);
		}
	}

}
