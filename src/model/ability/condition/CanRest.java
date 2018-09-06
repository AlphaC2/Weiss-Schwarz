package model.ability.condition;

import java.util.ArrayList;
import java.util.List;

import model.ability.condition.character.CharacterCondition;
import model.board.Board;
import model.board.Slot;
import model.card.Position;
import model.card.Character;

public class CanRest extends Condition {
	private List<CharacterCondition> conditions;
	
	public CanRest() {
		super("Can Rest");
		conditions = new ArrayList<>();
	}
	
	public void addCharCondition(CharacterCondition newCondition){
		conditions.add(newCondition);
	}
	
	@Override
	public boolean check(Board p1, Board p2) {
		List<Slot> slots = p1.getStage().getSlots();
		for (Slot slot : slots) {
			Character c = slot.getCharacter();
			if (c!= null && slot.getPosition() == Position.STANDING ){
				for (CharacterCondition characterCondition : conditions) {
					characterCondition.setCharacter(c);
					if (!characterCondition.check(p1, p2)){
						return false;
					}
					
				}
				return true;
			}
		}
		return false;
	}

}
