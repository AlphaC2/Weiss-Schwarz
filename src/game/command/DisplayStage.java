package game.command;

import game.controller.PlayerController;

public class DisplayStage extends Command{

	public DisplayStage() {
		super("Display Stage");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayStage();
		p2.displayStage();
	}

}
