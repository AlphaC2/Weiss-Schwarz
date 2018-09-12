package model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.PlayerController;
import controller.ReadUserInput;
import model.board.Board;
import model.board.Library;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.exceptions.EmptyLibraryException;

public class TestRefresh {
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
	ReadUserInput mockReader;
	
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
	}

	//Setup Test
	//Check Preconditions
	//Perform Actions
	//Check Postconditions
	@Test
	public void RefreshActionWithWaitingRoom(){
		//Setup Test
		Library library = board.getLibrary();
		List<Card> cards = library.getCards();
		Exception ex = null;
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				ex = e;
			}
		}
		WaitingRoom waitingRoom = board.getWaitingRoom();
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		
		//Check Preconditions
		assertNotNull(ex);		
		assertEquals(0, library.size());
		assertEquals(6, waitingRoom.size());
		assertEquals(0, board.getResolutionZone().size());
		
		//Perform Actions
		new Refresh().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(6, library.size());
		assertEquals(0, waitingRoom.size());
		assertEquals(0, board.getResolutionZone().size());
	}
	
	@Test
	public void RefreshActionWithoutWaitingRoom(){
		Library library = board.getLibrary();
		List<Card> cards = library.getCards();
		Exception ex = null;
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				ex = e;
			}
		}
		WaitingRoom waitingRoom = board.getWaitingRoom();

		//Check Preconditions
		assertNotNull(ex);		
		assertEquals(0, library.size());
		assertEquals(0, waitingRoom.size());
		
		//Perform Actions
		new Refresh().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(0, library.size());
		assertEquals(0, waitingRoom.size());
		verify(mockPlayerController).deckOut();
	}
}
