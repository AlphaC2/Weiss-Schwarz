package model.ability.action.condition;

import model.board.Slot;
import model.card.Character;

public class HasTrait extends Condition<Slot>{
	private String trait;
	
	public HasTrait(String trait) {
		super("Has trait");
		this.trait = trait;
	}
	
	@Override
	public boolean check() {
		if (trait != null){
			Character c = target.getCharacter();
			return c.getTraits().contains(trait.toUpperCase());
		}
		return false;
	}

	@Override
	public String toString() {
		return "HasTrait [trait=" + trait + "]";
	}

}
