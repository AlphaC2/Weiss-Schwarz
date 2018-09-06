package model.ability.condition;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.board.Stage;
import model.card.Card;
import model.card.Character;

public class TestCanRestOther {
	@Mock
	private Character mockTarget;
	
	@Mock
	private Character mockCaller;
	
	@Mock
	private Card mockCard;
	
	private Board board;
	private List<Card> deck;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);
	}
	
	@Test
	public void onlyCallerOnStage(){
		Stage stage = board.getStage();
		Slot callerSlot = stage.getSlot(SlotType.FRONT_CENTER);
		callerSlot.setCharacter(mockCaller);
		
		for (Slot slot : stage.getSlots()) {
			Character c = slot.getCharacter();
			assertTrue(c== null || c == mockCaller);
		}
		
		assertFalse(new CanRestOther(mockCaller).check(board, board));
	}
	
	@Test
	public void targetRestedOnStage(){
		Stage stage = board.getStage();
		Slot callerSlot = stage.getSlot(SlotType.FRONT_CENTER);
		callerSlot.setCharacter(mockCaller);
		
		for (Slot slot : stage.getSlots()) {
			Character c = slot.getCharacter();
			assertTrue(c== null || c == mockCaller);
		}
		
		assertFalse(new CanRestOther(mockCaller).check(board, board));
	}

}
