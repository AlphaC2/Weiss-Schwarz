package model.ability.action.condition;

import model.board.DamageZone;

public class CanLevelUp extends Condition<DamageZone>{

	public CanLevelUp() {
		super("Can level up");
	}

	@Override
	public boolean check() {
		return target.size() == DamageZone.cardsPerLevel;
	}

}
