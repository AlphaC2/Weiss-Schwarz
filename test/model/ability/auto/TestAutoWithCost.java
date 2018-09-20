package model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.GameManager;
import controller.PlayerController;
import io.CardXMLReader;
import io.Reader;
import io.Writer;
import model.ability.action.DiscardFromHand;
import model.ability.action.DrawToHand;
import model.ability.action.PayStock;
import model.ability.action.Rest;
import model.ability.action.TakeDamage;
import model.board.Board;
import model.board.DamageZone;
import model.board.Hand;
import model.board.Library;
import model.board.Slot;
import model.board.SlotType;
import model.board.Stage;
import model.board.Stock;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Position;
import model.gameEvent.EventType;

public class TestAutoWithCost {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private String path = "CardData\\DummySet\\";
	private static int testNumber = 0;
	private Library library;
	private DamageZone damage;
	private WaitingRoom waitingRoom;
	private Stage stage;
	private Hand hand;
	private Stock stock;
	private Character character;

	@Mock
	Reader mockReader;
	
	@Mock
	Writer mockWriter;

	@Before
	public void init() {
		testNumber++;
		System.out.println("\nTest Number " + testNumber);

		MockitoAnnotations.initMocks(this);
		character = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(character);
		}

		// Real Controller setup
		controller1 = new PlayerController("P1", mockReader, mockWriter);
		controller1.setDeck(deck);
		board = controller1.getBoard();
		
		controller2 = new PlayerController("P2", mockReader, mockWriter);
		controller2.setDeck(deck);
		
		// Gamemanager setup
		new GameManager(controller1, controller2);
		
		// Zone setup
		library = board.getLibrary();
		damage = board.getDamageZone();
		waitingRoom = board.getWaitingRoom();
		stage = board.getStage();
		hand = board.getHand();
		stock = board.getStock();
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void CannotPayCost(){
		// Setup Test
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, true, false);
		dummy.addCost(new PayStock(2));
		dummy.addCost(new Rest());
		dummy.addAction(new TakeDamage(1));
		dummy.addAction(new DrawToHand());
		character.addAbility(dummy);
		
		stage.place(character, SlotType.FRONT_CENTER);
		hand.add(character);
		hand.add(character);
		hand.add(character);
		hand.add(character);
		hand.add(character);
		dummy.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertFalse(dummy.canActivate());
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, stock.size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(6, hand.size());
		assertEquals(49, library.size());
		assertEquals(0, stock.size());
	}
	
	@Test
	public void CanPayTotalCostAndActions(){
		// Setup Test
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, true, false);
		dummy.addCost(new PayStock(2));
		dummy.addCost(new Rest());
		dummy.addAction(new TakeDamage(1));
		dummy.addAction(new DrawToHand());
		character.addAbility(dummy);
		
		Slot s = stage.getSlot(SlotType.FRONT_CENTER);
		stage.place(character, SlotType.FRONT_CENTER);

		doReturn(dummy, s).when(mockReader).getChoice(anyString(), anyList());
		
		hand.add(character);
		hand.add(character);
		hand.add(character);
		hand.add(character);
		hand.add(character);
		stock.add(character);
		stock.add(character);
		dummy.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(Position.STANDING, s.getPosition());
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertTrue(dummy.canActivate());
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		assertEquals(2, stock.size());
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		
		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(0, stock.size());
		assertEquals(1, damage.size());
		assertEquals(7, hand.size());
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(47, library.size());
		
	}

	@Test
	public void CanPayCostPerformActionsAsMuchAsYouCan(){
		// Setup Test
		new GameManager(controller2, controller1);
		Slot s = stage.getSlot(SlotType.FRONT_CENTER);
		stage.place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, false, false);
		dummy.addCost(new PayStock(2));
		dummy.addCost(new Rest());
		dummy.addAction(new DiscardFromHand());
		dummy.addAction(new DrawToHand());
		character.addAbility(dummy);
		doReturn(dummy, s).when(mockReader).getChoice(anyString(), anyList());
		stock.add(character);
		stock.add(character);
		dummy.setTargets(controller1,controller2);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(Position.STANDING, s.getPosition());
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(2, stock.size());
		assertEquals(0, waitingRoom.size());

		assertTrue(dummy.canActivate());
		
		// Perform Actions
		new DrawToHand().execute(controller2, controller1);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(1, hand.size());
		assertEquals(49, library.size());
		assertEquals(0, stock.size());
		assertEquals(2, waitingRoom.size());
	}
	
	@Test
	public void PayCostSkipDiscardPerformDraw(){
		// Setup Test
		new GameManager(controller2, controller1);
		Slot s = stage.getSlot(SlotType.FRONT_CENTER);
		stage.place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, false, false);
		dummy.addCost(new PayStock(2));
		dummy.addCost(new Rest());
		dummy.addAction(new DiscardFromHand(false));
		dummy.addAction(new DrawToHand());
		character.addAbility(dummy);
		doReturn(dummy, s).when(mockReader).getChoice(anyString(), anyList());
		stock.add(character);
		stock.add(character);
		hand.add(character);
		hand.add(character);
		hand.add(character);
		hand.add(character);
		dummy.setTargets(controller1,controller2);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(Position.STANDING, s.getPosition());
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		
		assertEquals(4, hand.size());
		assertEquals(50, library.size());
		assertEquals(2, stock.size());
		assertEquals(0, waitingRoom.size());

		assertTrue(dummy.canActivate());
		
		// Perform Actions
		new DrawToHand().execute(controller2, controller1);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(5, hand.size());
		assertEquals(49, library.size());
		assertEquals(0, stock.size());
		assertEquals(2, waitingRoom.size());
	}
	
}
