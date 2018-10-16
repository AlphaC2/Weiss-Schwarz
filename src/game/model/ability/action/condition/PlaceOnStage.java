package game.model.ability.action.condition;

import game.controller.PlayerController;
import game.model.ability.action.TargetedAction;

public class PlaceOnStage extends TargetedAction<Object>{

	PlaceOnStage() {
		super("Play Character On Stage");
	}

	@Override
	public String failureMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
 		// TODO Auto-generated method stub
		
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		// TODO Auto-generated method stub
		
	}

}
