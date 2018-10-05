package game.command;

import java.util.List;

import game.controller.PlayerController;
import game.model.board.Board;
import game.model.board.Slot;

public class DisplayStatus extends Command{

	public DisplayStatus() {
		super("Display Status");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		Board board = p1.getBoard();
		List<Slot> stage = board.getStage().getSlots();
		int stageChars = 0;
		for (Slot slot : stage) {
			if (slot.getCharacter() != null)
				stageChars++;
		}
		
		p1.log("Level " + board.getLevel().size());
		p1.log("Damage " + board.getDamageZone().size());
		p1.log("Hand " + board.getHand().size());
		p1.log("Stage " + stageChars);
		p1.log("Climax " + (board.climaxZone != null ? board.climaxZone : 0));
		p1.log("Stock " + board.getStock().size());
		p1.log("WaitingRoom " + board.getWaitingRoom().size());
		p1.log("Library " + board.getLibrary().size());
		p1.log("Memory " + board.getMemoryZone().size());
		p1.log("Resolution " + board.getResolutionZone().size());
		
		
		
	}

}
