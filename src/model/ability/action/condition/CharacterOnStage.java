package model.ability.action.condition;

import model.board.Slot;

public class CharacterOnStage extends Condition<Slot> {

	public CharacterOnStage() {
		super("Character On Stage");
	}

	@Override
	public boolean check() {
		return target.getCharacter() != null;
	}



}
