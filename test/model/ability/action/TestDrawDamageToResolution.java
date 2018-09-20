package model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.PlayerController;
import io.Reader;
import model.board.Board;
import model.board.Library;
import model.board.ResolutionZone;
import model.card.Card;
import model.card.Character;

public class TestDrawDamageToResolution {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	Character mockCharacter;
	
	@Mock
	Character mockCharacter2;
	
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
		board.getHand().add(mockCharacter);
		when(mockPlayerController.getBoard()).thenReturn(board);
		mockPlayerController.setReader(mockReader);
		when(mockPlayerController.isAlive()).thenReturn(true);
	}

	//Setup Test
	//Check Preconditions
	//Perform Actions
	//Check Postconditions
	@Test
	public void MoveOneCardFromLibraryToResolution(){
		//Setup Test
		Library library = board.getLibrary();
		library.placeTop(mockCharacter);
		
		assertEquals(51, library.size());
		assertEquals(mockCharacter, board.getLibrary().peek());
		assertEquals(0, board.getResolutionZone().size());
		
		//Perform Actions
		new DrawDamageToResolution().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(50, library.size());
		assertNotEquals(mockCharacter, library.peek());
		assertEquals(1, board.getResolutionZone().size());
		assertEquals(mockCharacter, board.getResolutionZone().getCards().get(0));
	}
	
	@Test
	public void MoveOneCardAndResolutionAlreadyHasCards(){
		//Setup Test
		Library library = board.getLibrary();
		library.placeTop(mockCharacter);
		ResolutionZone resolution = board.getResolutionZone(); 
		resolution.add(mockCard);
		resolution.add(mockCard);
		
		//Check Preconditions
		assertEquals(51, library.size());
		assertEquals(2, resolution.size());
		assertEquals(mockCharacter, board.getLibrary().peek());

		//Perform Actions
		new DrawDamageToResolution().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(50, library.size());
		assertNotEquals(mockCharacter, library.peek());
		assertEquals(3, board.getResolutionZone().size());
		assertEquals(mockCharacter, board.getResolutionZone().getCards().get(2));
	}
	
}
