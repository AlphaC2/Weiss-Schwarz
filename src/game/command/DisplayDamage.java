package game.command;

import game.controller.PlayerController;

public class DisplayDamage extends Command {

	public DisplayDamage() {
		super("Display Damage");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayDamageZone();
		p2.displayDamageZone();
	}

}
