package model.ability.condition.character;

import model.ability.condition.Condition;
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
			if(trait.equals(c.getTrait1()) || trait.equals(c.getTrait2())){
				return true;
			}
		}
		return false;
	}

}
