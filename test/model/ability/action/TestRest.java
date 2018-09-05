package model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.PlayerController;
import model.board.Board;
import model.board.Slot;
import model.card.Card;

public class TestRest {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);
		when(mockPlayerController.getBoard()).thenReturn(board);
	}
	
	@Test
	public void emptyStage(){
		List<Slot> slots = board.getStage().getSlots();
		for (Slot slot : slots) {
			assertNull(slot.getCharacter());
		}
		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(rest.failureMessage());
	}
}
