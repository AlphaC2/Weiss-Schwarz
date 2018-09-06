package model.ability.condition;


import model.board.Slot;
import model.card.Position;
import model.card.Character;

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
