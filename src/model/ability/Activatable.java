package model.ability;

import controller.PlayerController;

public interface Activatable {
	public abstract void execute(PlayerController p1, PlayerController p2);
}
