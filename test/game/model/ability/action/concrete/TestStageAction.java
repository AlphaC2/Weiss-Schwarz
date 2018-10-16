package game.model.ability.action.concrete;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.PlayerController;
import game.io.Reader;
import game.model.ability.AbilityInterface;
import game.model.ability.action.concrete.ChooseCharacterFromStage;
import game.model.ability.action.concrete.Rest;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.card.Card;
import game.model.card.Character;

import org.junit.runners.Parameterized;

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
	Reader mockReader;
	
	public TestStageAction(AbilityInterface action) {
		super();
		this.action = action;
	}

	 @Parameterized.Parameters
	   public static Collection<Object[]> actions() {
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
		when(mockPlayerController.isAlive()).thenReturn(true);
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
			e.printStackTrace();
		}
		verify(mockPlayerController).log(action.failureMessage());
	}
	
	
	
}
