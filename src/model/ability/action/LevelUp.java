package model.ability.action;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import model.ability.condition.CanLevelUp;
import model.board.Board;
import model.board.DamageZone;
import model.card.Card;

public class LevelUp extends Action<DamageZone> {

	public LevelUp() {
		super("Level Up");
		addCondition(new CanLevelUp());
	}

	@Override
	public String failureMessage() {
		return "Can't level up";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getDamageZone());
	}

	@Override
	public void executeAction(PlayerController p1, PlayerController p2) {
		Board board = p1.getBoard();
		DamageZone damage = targets.get(0);
		p1.log(p1.getPlayer().getName() + " Leveled Up");

		List<Card> levelCards = new ArrayList<Card>();
		for (int i = 0; i < DamageZone.cardsPerLevel; i++) {
			levelCards.add(damage.removeFromBottom());
		}
		Card chosen = p1.getChoice(p1.getPlayer().getName() + " choose a card to level up", levelCards);

		board.getLevel().add(chosen);
		levelCards.remove(chosen);
		board.getWaitingRoom().add(levelCards);
		
		if (p1.getBoard().getLevel().size() == 4){
			p1.level4();
		}

	}

}
