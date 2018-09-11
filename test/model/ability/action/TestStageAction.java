package model.ability.action;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import controller.PlayerController;
import controller.ReadUserInput;
import model.ability.AbilityInterface;
import model.board.Board;
import model.board.Slot;
import model.card.Card;
import model.card.Character;

@RunWith(Parameterized.class)
public class TestStageAction {
	private Board board;
	private AbilityInterface action;
	
	@Mock
	Card mockCard;
	
	@Mock
	Character mockCharacter;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Mock
	ReadUserInput mockReader;
	
	public TestStageAction(AbilityInterface action) {
		super();
		this.action = action;
	}

	 @Parameterized.Parameters
	   public static Collection actions() {
	      return Arrays.asList(new Object[][] {
	         { new Rest()},
	         { new ChooseCharacterFromStage()}
	      });
	 }
	 
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
		
		try {
			action.execute(mockPlayerController, mockPlayerController);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(mockPlayerController).log(action.failureMessage());
	}
	
	
	
}
