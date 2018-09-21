package command;

import controller.PlayerController;

public class DisplayLevel extends Command {

	public DisplayLevel() {
		super("Display Level");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayLevel();
		p2.displayLevel();
	}

}
