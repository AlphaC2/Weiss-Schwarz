package command;

import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.Slot;

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
