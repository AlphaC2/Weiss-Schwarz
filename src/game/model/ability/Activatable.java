package game.model.ability;

import game.controller.PlayerController;

public interface Activatable {
	public abstract void execute(PlayerController p1, PlayerController p2);
}
