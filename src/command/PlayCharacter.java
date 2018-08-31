package command;

import java.util.Arrays;
import java.util.List;

import controller.PlayerController;
import model.board.SlotType;
import model.card.Character;

public class PlayCharacter extends Command{

	public PlayCharacter() {
		super("Play Character");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		List<Character> list = p1.getBoard().getHand().getCardsOfType(Character.class);
		Character c = p1.getChoice("Choose a character to put on stage", list);
		SlotType s = p1.getChoice("Choose Slot", Arrays.asList(SlotType.values()));
		p1.getBoard().play(c, s);
	}

}
