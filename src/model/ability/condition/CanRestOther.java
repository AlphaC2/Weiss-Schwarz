package model.ability.condition;

import java.util.List;

import model.board.Board;
import model.board.Slot;
import model.card.Character;
import model.card.Position;

public class CanRestOther extends Condition {
	private Character c;
	private String trait;
	
	CanRestOther(Character c) {
		super("Rest Other Character");
		this.c = c;
		trait = null;
	}
	
	CanRestOther(Character c, String trait) {
		super("Rest Other Character");
		this.c = c;
		this.trait = trait; 
	}

	@Override
	public boolean check(Board p1, Board p2) {
		List<Slot> slots = p1.getStage().getSlots();
		for (Slot slot : slots) {
			if (slot.getCharacter() != null && slot.getCharacter() != c && slot.getPosition() == Position.STANDING ){
				if( trait == null ||
					trait.equals(slot.getCharacter().getTrait1()) ||
					trait.equals(slot.getCharacter().getTrait2())  ){
					return true;
				}
			}
		}
		return false;

	}

}
