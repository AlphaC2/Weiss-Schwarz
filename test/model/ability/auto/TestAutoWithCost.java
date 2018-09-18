package model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.ConsoleController;
import controller.GameManager;
import controller.PlayerController;
import controller.ReadUserInput;
import model.ability.action.Action;
import model.ability.action.DrawToHand;
import model.ability.action.PayStock;
import model.board.Board;
import model.board.DamageZone;
import model.board.Hand;
import model.board.LevelZone;
import model.board.Library;
import model.board.ResolutionZone;
import model.board.Slot;
import model.board.SlotType;
import model.board.Stage;
import model.board.Stock;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Climax;
import model.card.Position;
import model.gameEvent.EventType;
import model.player.Player;

public class TestAutoWithCost {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private ConsoleController realReader;
	private static int testNumber = 0;
	private Library library;
	private ResolutionZone resolution;
	private DamageZone damage;
	private WaitingRoom waitingRoom;
	private LevelZone level;
	private Stage stage;
	private Hand hand;
	private Stock stock;
	
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
		board = realReader.getBoard();
		
		controller2 = new ConsoleController("Real Player2");
		controller2.setReader(mockReader);
		controller2.setDeck(deck);
		
		// Mock Controller setup
		when(mockPlayerController.getBoard()).thenReturn(board);
		when(mockPlayerController.getPlayer()).thenReturn(mockPlayer);
		mockPlayerController.setReader(mockReader);
		doReturn("mockPlayer").when(mockPlayer).getName();
		
		// Gamemanager setup
		new GameManager(realReader, controller2);
		
		// Zone setup
		library = board.getLibrary();
		resolution = board.getResolutionZone();
		damage = board.getDamageZone();
		waitingRoom = board.getWaitingRoom();
		level = board.getLevel();
		stage = board.getStage();
		hand = board.getHand();
		stock = board.getStock();
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Ignore
	@Test
	public void CannotPayCost(){
		// Setup Test
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoWithCost(mockCharacter, EventType.DREW_CARD, true, false);
		List<AutoAbility> dummyList = new ArrayList<>();
		dummyList.add(dummy);
		when(mockCharacter.getAutoAbilities()).thenReturn(dummyList);
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertFalse(dummy.canActivate());
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, stock.size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(6, hand.size());
		assertEquals(49, library.size());
		assertEquals(0, stock.size());
	}
	
	@Ignore
	@Test
	public void CanPayPartialCost(){
		// Setup Test
		Slot s = stage.getSlot(SlotType.FRONT_CENTER);
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		s.rest();
		AutoAbility dummy = new DummyAutoWithCost(mockCharacter, EventType.DREW_CARD, true, false);
		List<AutoAbility> dummyList = new ArrayList<>();
		dummyList.add(dummy);
		when(mockCharacter.getAutoAbilities()).thenReturn(dummyList);
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		stock.add(mockCard);
		stock.add(mockCard);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertFalse(dummy.canActivate());
		
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		assertEquals(2, stock.size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(6, hand.size());
		assertEquals(49, library.size());
		assertEquals(2, stock.size());
	}

	
	@Test
	public void CanPayTotalCostAndActions(){
		// Setup Test
		Slot s = stage.getSlot(SlotType.FRONT_CENTER);
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoWithCost(mockCharacter, EventType.DREW_CARD, true, false);
		List<AutoAbility> dummyList = new ArrayList<>();
		dummyList.add(dummy);
		when(mockCharacter.getAutoAbilities()).thenReturn(dummyList);
		//when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);
		//doReturn(dummyList).when(mockCharacter.getAutoAbilities());
		//doReturn(dummy, s).when(mockReader.getChoice(anyString(), anyList()));
		
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		stock.add(mockCard);
		stock.add(mockCard);
		dummy.setTargets(realReader, controller2);
		
		// Check Preconditions
		assertEquals(Position.STANDING, s.getPosition());
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
//		assertTrue(dummy.canActivate());
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		assertEquals(2, stock.size());
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		
		// Perform Actions
		new DrawToHand().execute(realReader, controller2);
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertEquals(0, stock.size());
		assertEquals(1, damage.size());
		assertEquals(7, hand.size());
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(47, library.size());
		
	}
}
