package game.model.ability.action.concrete;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.GameManager;
import game.controller.PlayerController;
import game.io.CardXMLReader;
import game.io.Reader;
import game.io.Writer;
import game.model.ability.action.concrete.DiscardFromHand;
import game.model.ability.action.condition.MatchesCardType;
import game.model.board.Board;
import game.model.board.Hand;
import game.model.board.Library;
import game.model.board.WaitingRoom;
import game.model.card.Card;
import game.model.card.CardType;
import game.model.card.Character;
import game.model.card.Climax;
import game.model.card.Event;

public class TestDiscard {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private Library library;
	private WaitingRoom waitingRoom;
	private Hand hand;
	private Character character;
	private Climax climax;
	private Event event;
	
	@Mock
	Card mockCard;
	
	@Mock
	Reader mockReader;

	@Mock
	Writer mockWriter;
	
	@Before
	public void init() {
		testNumber++;
		System.out.println("\nTest Number " + testNumber);

		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		
		// Real Controller setup
		controller1 = new PlayerController("P1", mockReader, mockWriter);
		controller1.setDeck(deck);
		board = controller1.getBoard();
		
		controller2 = new PlayerController("P2", mockReader, mockWriter);
		controller2.setDeck(deck);
		
		// Gamemanager setup
		new GameManager(controller1, controller2);
		
		// Card Setup
		character = (Character) CardXMLReader.read("CardData\\DummySet\\BasicCharacter.xml");
		climax = (Climax) CardXMLReader.read("CardData\\DummySet\\DummyClimax.xml");
		event = (Event) CardXMLReader.read("CardData\\DummySet\\DummyEvent.xml");
		
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
		hand.add(character);
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(character);
		
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
		hand.add(character);
		hand.add(climax);
		DiscardFromHand discard = new DiscardFromHand();
		discard.addCondition(new MatchesCardType(CardType.CHARACTER));
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(character);
		
		// Check Preconditions
		assertEquals(2, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		discard.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(1, hand.size());
		assertEquals(climax, hand.getCards().get(0));
		assertEquals(50, library.size());
		assertEquals(1, waitingRoom.size());
		assertEquals(character, waitingRoom.getCards().get(0));
	}
	
	@Test
	public void DiscardWithNoneMeetingTypeFromHand(){
		// Setup Test
		hand.add(event);
		hand.add(climax);
		DiscardFromHand discard = new DiscardFromHand();
		discard.addCondition(new MatchesCardType(CardType.CHARACTER));
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(character);
		
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
		hand.add(character);
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(character);
		
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
