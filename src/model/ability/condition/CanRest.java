package model.ability.condition;

import java.util.List;

import model.board.Board;
import model.board.Slot;
import model.card.Position;

public class CanRest extends Condition {
	private String trait;
	
	public CanRest() {
		super("Can Rest");
		trait = null;
	}
	
	public CanRest(String trait) {
		super("Can Rest");
		this.trait = trait;
	}

	@Override
	public boolean check(Board p1, Board p2) {
		List<Slot> slots = p1.getStage().getSlots();
		for (Slot slot : slots) {
			if (slot.getCharacter() != null && slot.getPosition() == Position.STANDING	){
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
