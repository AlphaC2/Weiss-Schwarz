package command;

import java.util.Iterator;
import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.SlotType;
import model.card.Character;
import model.card.Position;

public class Encore extends Command{

	public Encore() {
		super("Encore");
	}
	
	private void encore(PlayerController pc){
		Board board = pc.getBoard();
		List<Character> dead = board.getStage().getCharacterByPosition(Position.REVERSED);
		Iterator<Character> iterator = dead.iterator();
		Character current;
		SlotType s;
		while (iterator.hasNext()) {
			current = iterator.next();
			s = board.getStage().getSlot(current).getSlotType();
			board.getWaitingRoom().add(current);
			board.getStage().removeCharacter(s);
			boolean choice = pc.getChoice(pc.getPlayer().getName() + " Encore:" + current.toShortString() + "?");
			if (choice) {
				new PayStock(3).execute(pc, null);
				board.getWaitingRoom().remove(current);
				board.getStage().place(current, s);
				pc.log(current.toShortString() + " encored");
			}
		}
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		encore(p1);
		encore(p2);
	}

}
