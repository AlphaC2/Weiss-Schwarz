package model.ability.action;

import controller.PlayerController;
import model.board.Library;

public class ShuffleLibrary extends Action<Library>{

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
