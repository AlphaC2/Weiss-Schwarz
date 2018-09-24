package game.command;

import game.controller.PlayerController;

public class EndPhase extends Command {

	public EndPhase( ) {
		super("End Phase");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.getPlayer().endPhase();
	}

}
