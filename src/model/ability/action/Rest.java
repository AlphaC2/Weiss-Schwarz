package model.ability.action;

import controller.PlayerController;
import model.ability.condition.CanRest;
import model.board.Slot;

public class Rest extends Action<Slot> {

	public Rest() {
		super("Rest");
		addCondition(new CanRest());
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		//Random Comment
		targets.addAll(p1.getBoard().getStage().getSlots());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		Slot chosen = p1.getChoice("Choose a character to rest:", targets);
		chosen.rest();
	}

	@Override
	public String failureMessage() {
		return "Could not Rest";
	}


}
