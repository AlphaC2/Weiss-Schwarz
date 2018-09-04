package command;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.DamageZone;
import model.card.Card;

public class LevelUp extends Command {

	public LevelUp() {
		super("Level Up");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		Board board = p1.getBoard();
		DamageZone damage = board.getDamageZone();
		if (damage.size() >= DamageZone.cardsPerLevel){
			p1.log(p1.getPlayer().getName() + " Leveled Up");
			
			List<Card> levelCards = new ArrayList<Card>();
			for (int i = 0; i < DamageZone.cardsPerLevel; i++) {
				levelCards.add(damage.removeFromBottom());
			}
			Card chosen = p1.getChoice(p1.getPlayer().getName() + " choose a card to level up", levelCards);

			board.getLevel().add(chosen);
			levelCards.remove(chosen);
			board.getWaitingRoom().add(levelCards);
			
		}
		
	}

}
