package model.ability.action;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.PlayerController;
import controller.ReadUserInput;
import model.board.Board;
import model.board.Slot;
import model.card.Card;
import model.card.Character;

public class TestChooseCharacterFromStage {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	Character mockCharacter;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Mock
	ReadUserInput mockReader;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);
		when(mockPlayerController.getBoard()).thenReturn(board);
		mockPlayerController.setReader(mockReader);
	}
	
	@Test
	public void emptyStage(){
		List<Slot> slots = board.getStage().getSlots();
		for (Slot slot : slots) {
			assertNull(slot.getCharacter());
		}
		
		Action<Slot> action = new ChooseCharacterFromStage();
		action.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(action.failureMessage());
	}
	
	@Test
	public void SingleCharacterOnStage(){
		List<Slot> slots = board.getStage().getSlots();
		for (Slot slot : slots) {
			assertNull(slot.getCharacter());
		}
		
		Action<Slot> action = new ChooseCharacterFromStage();
		action.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(action.failureMessage());
	}
}
