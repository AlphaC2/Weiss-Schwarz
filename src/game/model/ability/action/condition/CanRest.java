package game.model.ability.action.condition;


import game.model.board.Slot;
import game.model.card.Character;
import game.model.card.Position;

public class CanRest extends Condition<Slot> {

	public CanRest() {
		super("Can Rest");
	}


	@Override
	public boolean check() {
		Character c = target.getCharacter();
		if (c != null && target.getPosition() == Position.STANDING) {
			return true;
		}
		return false;
	}

}
