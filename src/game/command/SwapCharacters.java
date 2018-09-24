package game.command;

import java.util.List;

import game.controller.PlayerController;
import game.model.board.Slot;
import game.model.card.Card;
import game.model.card.Character;
import game.model.card.Position;

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
