package model.ability.condition.character;

import model.ability.condition.Condition;
import model.card.Character;

public abstract class CharacterCondition extends Condition{
	private Character c;
	
	protected CharacterCondition(String name) {
		super(name);
	}

	public void setCharacter(Character c){
		this.c = c;
	}
	
	protected Character getCharacter(){
		return c;
	}
}
