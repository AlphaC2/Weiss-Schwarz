package model.ability.condition.character;

import model.board.Board;
import model.card.Character;

public class HasTrait extends CharacterCondition{
	private String trait;
	
	public HasTrait(String trait) {
		super("Has trait");
		this.trait = trait;
	}
	
	@Override
	public boolean check(Board p1, Board p2) {
		if (trait != null){
			Character c = getCharacter();
			if(trait.equals(c.getTrait1()) || trait.equals(c.getTrait2())){
				return true;
			}
		}
		return false;
	}

}
