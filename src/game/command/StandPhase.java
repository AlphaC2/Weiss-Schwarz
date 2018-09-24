package game.command;

import java.util.List;

import game.controller.PlayerController;
import game.model.board.Board;
import game.model.board.Slot;

public class StandPhase extends Command {

	public StandPhase() {
		super("Stand Phase");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		Board board = p1.getBoard();
		
		List<Slot> slots = board.getStage().getSlots();
		for (Slot slot : slots) {
			slot.stand();
		}
	}

}
