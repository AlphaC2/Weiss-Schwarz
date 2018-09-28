package game.model.ability.action;

import game.controller.PlayerController;

public abstract class DelayedTargetAction<T> extends Action<T> {

	protected DelayedTargetAction(String name) {
		super(name);
	}
	
	protected DelayedTargetAction(String name, boolean isRequired) {
		super(name, isRequired);
	}
	

	
	@Override
	public final void execute(PlayerController p1, PlayerController p2) {
		if (gameOver(p1, p2)){
			return;
		}

		executeChain(p1, p2);
		
	}

	
}
