package game.model.ability.action.concrete;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.PlayerController;
import game.io.Reader;
import game.model.ability.action.TargetedAction;
import game.model.ability.action.concrete.ChooseCharacterFromStage;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.card.Card;

public class TestChooseCharacterFromStage {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Mock
	Reader mockReader;
	
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
		when(mockPlayerController.isAlive()).thenReturn(true);
	}
	
	@Test
	public void emptyStage(){
		List<Slot> slots = board.getStage().getSlots();
		for (Slot slot : slots) {
			assertNull(slot.getCharacter());
		}
		
		TargetedAction<Slot> action = new ChooseCharacterFromStage();
		try {
			action.execute(mockPlayerController, mockPlayerController);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		verify(mockPlayerController).log(action.failureMessage());
	}
	
	@Test
	public void SingleCharacterOnStage(){
		List<Slot> slots = board.getStage().getSlots();
		for (Slot slot : slots) {
			assertNull(slot.getCharacter());
		}
		
		TargetedAction<Slot> action = new ChooseCharacterFromStage();
		try {
			action.execute(mockPlayerController, mockPlayerController);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		verify(mockPlayerController).log(action.failureMessage());
	}
}
