package command;

import controller.PlayerController;

public class DisplayHand extends Command{

	public DisplayHand() {
		super("Display Hand");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.getPlayer().displayHand();
	}

}
