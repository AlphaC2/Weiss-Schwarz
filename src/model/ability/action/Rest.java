package model.ability.action;

import java.util.Iterator;
import java.util.List;

import controller.PlayerController;
import model.ability.condition.CanRest;
import model.ability.condition.Condition;
import model.board.Slot;
import model.card.Character;
import model.card.Position;

public class Rest extends Action {
	private String trait;
	
	public Rest() {
		super("Rest");
		Condition c = new CanRest();
		addCondition(c);
	}
	
	public Rest(String trait) {
		super("Rest");
		this.trait = trait;
		Condition c = new CanRest(trait);
		addCondition(c);
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		List<Slot> slots = p1.getBoard().getStage().getCharacterByPosition(Position.STANDING);
		//Remove any chars that don't meet the trait requirement, if any requirement exists
		if (trait != null){
			Iterator<Slot> slotIterator = slots.iterator();
			while(slotIterator.hasNext()){
				Slot s = slotIterator.next();
				Character c = s.getCharacter();
				if (!trait.equals(c.getTrait1()) && !trait.equals(c.getTrait2())){
					slotIterator.remove();
				}
			}
			
		}
		Slot chosen = p1.getChoice("Choose a character to rest:", slots);
		chosen.rest();
		
	}

	@Override
	protected String failureMessage() {
		return "Could not Rest";
	}

}
