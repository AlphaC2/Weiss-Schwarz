package game.model.ability.action.concrete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.mockito.*;

import game.controller.PlayerController;
import game.io.ConsoleReader;
import game.model.ability.action.concrete.DrawToHand;
import game.model.board.Board;
import game.model.board.Hand;
import game.model.board.Library;
import game.model.card.Card;
import game.model.player.Player;

public class TestDrawToHand {
	private Board board;
	private Library library;
	private Hand hand;
	
	@Mock
	Card mockCard;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Mock
	private ConsoleReader reader;
	
	@Mock
	Player mockPlayer;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		
		board = new Board(deck);
		library = board.getLibrary();
		hand = board.getHand();
		
		when(mockPlayerController.getBoard()).thenReturn(board);
		when(mockPlayerController.getPlayer()).thenReturn(mockPlayer);
		when(mockPlayer.getName()).thenReturn("p1");
		when(mockPlayerController.isAlive()).thenReturn(true);
	}
	
	@Test
	public void DrawToHand1CardToHand(){
		//Setup Test
		
		
		//Check Preconditions
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		
		//Perform Actions
		new DrawToHand().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(1, hand.size());
		assertEquals(49, library.size());
	}
	
	@Test
	public void DrawToHand1CardNotFromDamageZone(){
		int sizeBefore = mockPlayerController.getBoard().getDamageZone().size();
		new DrawToHand().execute(mockPlayerController, null);
		assertEquals(sizeBefore, mockPlayerController.getBoard().getDamageZone().size());
	}
	
	@Test
	public void DrawToHand1CardNotFromLevelZone(){
		int sizeBefore = mockPlayerController.getBoard().getLevel().size();
		new DrawToHand().execute(mockPlayerController, null);
		assertEquals(sizeBefore, mockPlayerController.getBoard().getLevel().size());
	}
	
	@Test
	public void DrawToHand1CardNotFromMemoryZone(){
		int sizeBefore = mockPlayerController.getBoard().getMemoryZone().size();
		new DrawToHand().execute(mockPlayerController, null);
		assertEquals(sizeBefore, mockPlayerController.getBoard().getMemoryZone().size());
	}
	
	@Test
	public void DrawToHand1CardNotFromStage(){
		int sizeBefore = mockPlayerController.getBoard().getStage().getCharacters().size();
		new DrawToHand().execute(mockPlayerController, null);
		assertEquals(sizeBefore, mockPlayerController.getBoard().getStage().getCharacters().size());
	}
	
	@Test
	public void DrawToHand1CardNotFromStock(){
		int sizeBefore = mockPlayerController.getBoard().getStock().size();
		new DrawToHand().execute(mockPlayerController, null);
		assertEquals(sizeBefore, mockPlayerController.getBoard().getStock().size());
	}
	
	@Test
	public void DrawToHand1CardNotFromWaitingRoom(){
		int sizeBefore = mockPlayerController.getBoard().getWaitingRoom().size();
		new DrawToHand().execute(mockPlayerController, null);
		assertEquals(sizeBefore, mockPlayerController.getBoard().getWaitingRoom().size());
	}
}
