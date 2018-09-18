package model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.ConsoleController;
import controller.GameManager;
import controller.PlayerController;
import controller.ReadUserInput;
import model.ability.condition.MatchesCardType;
import model.board.*;
import model.card.*;
import model.card.Character;
import model.player.Player;

public class TestDiscard {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private ConsoleController realReader;
	private static int testNumber = 0;
	private Library library;
	private WaitingRoom waitingRoom;
	private Hand hand;
	
	//@Rule
	//public final ExpectedSystemExit exit;
	
	@Mock
	Card mockCard;

	@Mock
	Character mockCharacter;

	@Mock
	Character mockCharacter2;

	@Mock
	Climax mockClimax;
	
	@Mock
	Event mockEvent;

	@Mock
	PlayerController mockPlayerController;

	@Mock
	ReadUserInput mockReader;

	@Mock
	Player mockPlayer;

	@Before
	public void init() {
		testNumber++;
		System.out.println("\nTest Number " + testNumber);

		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		
		// Real Reader setup
		realReader = new ConsoleController("P1 RealReader");
		realReader.setDeck(deck);

		// Real Controller setup
		controller1 = new ConsoleController("Real Player");
		controller1.setReader(mockReader);
		controller1.setDeck(deck);
		
		controller2 = new ConsoleController("Real Player2");
		controller2.setReader(mockReader);
		controller2.setDeck(deck);
		
		// Mock Controller setup
		when(mockPlayerController.getBoard()).thenReturn(board);
		when(mockPlayerController.getPlayer()).thenReturn(mockPlayer);
		mockPlayerController.setReader(mockReader);
		doReturn("mockPlayer").when(mockPlayer).getName();
		
		// Gamemanager setup
		new GameManager(controller1, controller2);
		
		// Zone setup
		board = controller1.getBoard();
		library = board.getLibrary();
		waitingRoom = board.getWaitingRoom();
		hand = board.getHand();
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void DiscardFromEmptyHand(){
		// Setup Test
		
		// Check Preconditions
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		new DiscardFromHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		
	}
	
	@Test
	public void DiscardCardFromHand(){
		// Setup Test
		hand.add(mockCard);
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(mockCard);
		//doReturn(mockCard).when(mockReader.getChoice(anyString(), anyList()));
		
		// Check Preconditions
		assertEquals(1, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());

		// Perform Actions
		new DiscardFromHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(1, waitingRoom.size());
	}
	
	@Test
	public void DiscardCardMeetingTypeFromHand(){
		// Setup Test
		hand.add(mockCharacter);
		hand.add(mockClimax);
		DiscardFromHand discard = new DiscardFromHand();
		discard.addCondition(new MatchesCardType(CardType.CHARACTER));
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(mockCharacter);
		
		// Check Preconditions
		assertEquals(2, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		discard.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(1, hand.size());
		assertEquals(mockClimax, hand.getCards().get(0));
		assertEquals(50, library.size());
		assertEquals(1, waitingRoom.size());
		assertEquals(mockCharacter, waitingRoom.getCards().get(0));
	}
	
	@Test
	public void DiscardWithNoneMeetingTypeFromHand(){
		// Setup Test
		hand.add(mockEvent);
		hand.add(mockClimax);
		DiscardFromHand discard = new DiscardFromHand();
		discard.addCondition(new MatchesCardType(CardType.CHARACTER));
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(mockCharacter);
		
		// Check Preconditions
		assertEquals(2, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		discard.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(2, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
	}
	
	@Test
	public void MultipleDiscard(){
		// Setup Test
		hand.add(mockCharacter);
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(mockCharacter);
		
		// Check Preconditions
		assertEquals(1, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		new DiscardFromHand().execute(controller1, controller2);
		new DiscardFromHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(1, waitingRoom.size());
	}
	
	
	
}
