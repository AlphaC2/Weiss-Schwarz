package model.ability.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controller.PlayerController;
import model.ability.condition.CanRest;
import model.ability.condition.Condition;
import model.ability.condition.character.CharacterCondition;
import model.board.Slot;
import model.card.Character;
import model.card.Position;

public class Rest extends Action {
	private List<CharacterCondition> conditions;
	
	public Rest() {
		super("Rest");
		Condition condition = new CanRest();
		addCondition(condition);
		conditions = new ArrayList<>();
	}
	
	public Rest(CharacterCondition cCondition){
		super("Rest");
		conditions = new ArrayList<>();
		CanRest condition = new CanRest();
		condition.addCharCondition(cCondition);
		addCondition(condition);
		conditions.add(cCondition);
	}
	
	public void addCharCondition(CharacterCondition newCondition){
		conditions.add(newCondition);
	}
	
	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		List<Slot> slots = p1.getBoard().getStage().getCharacterByPosition(Position.STANDING);
		//Remove any chars that don't meet the requirement, if any requirement exists
			Iterator<Slot> slotIterator = slots.iterator();
			while(slotIterator.hasNext()){
				Slot s = slotIterator.next();
				Character c = s.getCharacter();
				for (CharacterCondition characterCondition : conditions) {
					
					if(!characterCondition.check(p1.getBoard(), p2.getBoard())){
						slotIterator.remove();
						break;
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
