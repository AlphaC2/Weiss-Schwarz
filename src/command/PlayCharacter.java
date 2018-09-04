package command;

import java.util.Arrays;
import java.util.List;

import controller.PlayerController;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Character;

public class PlayCharacter extends Command{

	public PlayCharacter() {
		super("Play Character");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		Board board = p1.getBoard();
		List<Character> list = board.getHand().getCardsOfType(Character.class);
		Character c = p1.getChoice("Choose a character to put on stage", list);
		SlotType slotType = p1.getChoice("Choose Slot", Arrays.asList(SlotType.values()));
		//p1.getBoard().play(c, s);
		
		Slot slot = board.getStage().getSlot(slotType);
		if (slot.getCharacter() != null){
			board.getWaitingRoom().add( board.getStage().removeCharacter(slotType) );
		}
		board.getHand().remove(c);
		slot.setCharacter(c);
		
			
		
	}

}
