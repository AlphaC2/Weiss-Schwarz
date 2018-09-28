package game.model.ability.action.concrete;

import game.controller.PlayerController;
import game.model.ability.action.TargetedAction;
import game.model.board.Library;

public class ShuffleLibrary extends TargetedAction<Library>{

	ShuffleLibrary() {
		super("Shuffle Library");
	}

	@Override
	public String failureMessage() {
		return "Can't shuffle library";
	}

	@Override
	protected void setTargets(PlayerController p1, PlayerController p2) {
		targets.add(p1.getBoard().getLibrary());
	}

	@Override
	protected void executeAction(PlayerController p1, PlayerController p2) {
		targets.get(0).shuffle();
		p1.log("Shuffled library");
	}

}
