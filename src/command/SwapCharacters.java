package command;

import java.util.List;

import controller.PlayerController;
import model.board.Slot;
import model.card.Card;
import model.card.Character;
import model.card.Position;

public class SwapCharacters extends Command {

	public SwapCharacters() {
		super("Swap Characters");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
		p1.displayStage();
		p2.displayStage();
		
		List<Slot> slots = p1.getBoard().getStage().getSlots();
		
		Slot s1 = p1.getChoice("Choose a slot", slots);
		Slot s2 = p1.getChoice("Choose another slot", slots);
		
		Character char1 = s1.getCharacter();
		Position position = s1.getPosition();
		List<Card> markers = s1.getMarkers();
		
		s1.setCharacter(s2.getCharacter());
		s1.setPosition(s2.getPosition());
		s1.setMarkers(s2.getMarkers());
		
		s2.setCharacter(char1);
		s2.setPosition(position);
		s2.setMarkers(markers);

	}

}
