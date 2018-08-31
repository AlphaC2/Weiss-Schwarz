package command;

import controller.PlayerController;

public class DisplayWaitingRoom extends Command{

	public DisplayWaitingRoom () {
		super("Display Waiting Room");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayWaitingRoom();
	}

}
