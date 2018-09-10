package model.ability.condition;

import model.board.Slot;
import model.card.Karacter;

public class HasTrait extends Condition<Slot>{
	private String trait;
	
	public HasTrait(String trait) {
		super("Has trait");
		this.trait = trait;
	}
	
	@Override
	public boolean check() {
		if (trait != null){
			Karacter c = target.getCharacter();
			if(trait.equalsIgnoreCase(c.getTrait1()) || trait.equalsIgnoreCase(c.getTrait2())){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "HasTrait [trait=" + trait + "]";
	}

}
