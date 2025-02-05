package game.model.ability.action.condition;

import java.util.List;

import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.LevelZone;
import game.model.card.Card;
import game.model.card.Character;

public class HasColor extends Condition<Card> {

	private LevelZone level;
	private DamageZone damage;

	public HasColor() {
		super("Has Color");
	}

	public void init(Board board) {
		this.level = board.getLevel();
		this.damage = board.getDamageZone();
	}

	@Override
	public boolean check() {

		if (!(target instanceof Character) || target.getLevel() != 0) {
			return hasColour(level.getCards()) || hasColour(damage.getCards());
		} else {
			return true;
		}
	}

	private boolean hasColour(List<Card> cards) {
		for (Card card : cards) {
			if (card.isFaceUp() && card.getColour() == target.getColour()) {
				return true;
			}
		}
		return false;
	}

}
