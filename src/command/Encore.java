package command;

import java.util.Iterator;
import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.SlotType;
import model.card.Character;

public class Encore extends Command{

	public Encore() {
		super("Encore");
	}
	
	private void encore(PlayerController pc){
		Board board = pc.getBoard();
		List<Character> dead = board.getReversed();
		Iterator<Character> iterator = dead.iterator();
		Character current;
		SlotType s;
		while (iterator.hasNext()) {
			current = iterator.next();
			s = board.getSlot(current).getSlotType();
			board.sendToWaitingRoom(current);
			board.remove(s);
			boolean choice = pc.getChoice(pc.getPlayer().getName() + " Encore:" + current.toShortString() + "?(y/n)");
			if (choice) {
				if (board.payCost(3)) {
					board.salvage(current);
					board.play(current, s);
					pc.log(current.toShortString() + " encored");

				} else {
					pc.log("Not enough stock to encore");
				}
			}

		}
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		encore(p1);
		encore(p2);
		p1.getBoard().endClimax();
	}

}
