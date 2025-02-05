package game.model.ability.action.concrete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.PlayerController;
import game.io.Reader;
import game.model.ability.action.concrete.Refresh;
import game.model.board.Board;
import game.model.board.Library;
import game.model.board.WaitingRoom;
import game.model.card.Card;
import game.model.card.Character;
import game.model.exceptions.EmptyLibraryException;

public class TestRefresh {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	Character mockCharacter;
	
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
