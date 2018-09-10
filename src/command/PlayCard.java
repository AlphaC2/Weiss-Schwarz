package command;

import java.util.Arrays;
import java.util.List;

import controller.PlayerController;
import model.ability.Ability;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Card;
import model.card.Event;
import model.card.Karacter;

public class PlayCard extends Command {

	public PlayCard() {
		super("Play Card");
	}

	@Override
	public void execute(PlayerController p1, PlayerController p2) {
	
		new PlayCard();

	}

}
