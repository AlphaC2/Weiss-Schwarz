package model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.ConsoleController;
import controller.GameManager;
import controller.PlayerController;
import controller.ReadUserInput;
import model.ability.action.PayStock;
import model.ability.action.TakeDamage;
import model.board.Board;
import model.board.DamageZone;
import model.board.Hand;
import model.board.LevelZone;
import model.board.Library;
import model.board.SlotType;
import model.board.Stage;
import model.board.Stock;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Climax;
import model.gameEvent.EventType;
import model.player.PhaseTiming;
import model.player.PlayerPhase;

public class TestPhaseTransitionAbilities {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private Library library;
	private DamageZone damage;
	private WaitingRoom waitingRoom;
	private Stage stage;
	private Hand hand;
	private Stock stock;
	private LevelZone level;
	private AutoAbility dummy;
	private List<AutoAbility> dummyList;
	private GameManager gm;
	
	@Mock
	Card mockCard;

	@Mock
	Character mockCharacter;

	@Mock
	Character mockCharacter2;
	
	@Mock
	Character mockCharacter3;
	
	@Mock
	Climax mockClimax;

	@Mock
	ReadUserInput mockReader;

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
		controller1 = new ConsoleController("P1");
		controller1.setReader(mockReader);
		controller1.setDeck(deck);
		board = controller1.getBoard();

		controller2 = new ConsoleController("P2");
		controller2.setReader(mockReader);
		controller2.setDeck(deck);

		// Gamemanager setup
		gm = new GameManager(controller1, controller2);

		// Zone setup
		library = board.getLibrary();
		damage = board.getDamageZone();
		waitingRoom = board.getWaitingRoom();
		stage = board.getStage();
		hand = board.getHand();
		stock = board.getStock();
		level = board.getLevel();
		
		// Setup Dummy Ability
		dummy = new DummyPhaseAutoAbility(mockCharacter, true, PlayerPhase.DRAW, PhaseTiming.START);		
		dummy.addCost(new PayStock(1));
		dummy.addAction(new TakeDamage(1));
		dummyList = new ArrayList<>();
		dummyList.add(dummy);
		doReturn(dummyList).when(mockCharacter).getAutoAbilities();
		doReturn(dummy).when(mockReader).getChoice(anyString(), anyList());
		
		//Basic Preconditions
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
	}
	
	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	
	@Test
	public void BeginningOfDrawPhaseTrigger(){
		// Setup Test
		controller1.getPlayer().endPhase();
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		stock.add(mockCard);
		dummy.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(PlayerPhase.STAND ,controller1.getPlayer().getPhase());
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, damage.size());
		assertEquals(1, stock.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		controller1.getPlayer().endPhase();
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(PlayerPhase.DRAW,controller1.getPlayer().getPhase());
		assertEquals(0, hand.size());
		
		assertEquals(0, stock.size());
		assertEquals(1, damage.size());
		assertEquals(49, library.size());
		assertEquals(1, waitingRoom.size());
		
	}

	@Test
	public void TwoAutoAbilitiesTriggerDifferentTimes(){
		// Setup Test
		PhaseAutoAbility dummy2 = new DummyPhaseAutoAbility(mockCharacter2, true, PlayerPhase.STAND, PhaseTiming.END);
		dummy2.addCost(new PayStock(1));
		dummy2.addAction(new TakeDamage(1));
		ArrayList<PhaseAutoAbility> dummyList2 = new ArrayList<>();
		dummyList2.add(dummy2);
		doReturn(dummyList2).when(mockCharacter2).getAutoAbilities();
		doReturn(dummy2, dummy).when(mockReader).getChoice(anyString(), anyList());
		
		controller1.getPlayer().endPhase();
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		stage.place(mockCharacter2, SlotType.FRONT_LEFT);
		stock.add(mockCard);
		stock.add(mockCard);
		dummy.setTargets(controller1, controller2);
		dummy2.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(mockCharacter2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		assertEquals(dummy2, mockCharacter2.getAutoAbilities().get(0));
		assertEquals(PlayerPhase.STAND,controller1.getPlayer().getPhase());

		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, damage.size());
		assertEquals(2, stock.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		controller1.getPlayer().endPhase();
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(mockCharacter2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		assertEquals(dummy2, mockCharacter2.getAutoAbilities().get(0));
		assertEquals(PlayerPhase.DRAW,controller1.getPlayer().getPhase());
		assertEquals(0, hand.size());
		assertEquals(0, stock.size());
		assertEquals(2, damage.size());
		assertEquals(48, library.size());
		assertEquals(2, waitingRoom.size());
		
	}

	@Test
	public void ThreeAutoAbilitiesTriggerDifferentTimes(){
		// Setup Test
		//Ability 2 setup
		PhaseAutoAbility dummy2 = new DummyPhaseAutoAbility(mockCharacter2, true, PlayerPhase.DRAW, PhaseTiming.END);
		dummy2.addCost(new PayStock(1));
		dummy2.addAction(new TakeDamage(1));
		ArrayList<PhaseAutoAbility> dummyList2 = new ArrayList<>();
		dummyList2.add(dummy2);
		doReturn(dummyList2).when(mockCharacter2).getAutoAbilities();

		//Ability 3
		AutoAbility dummy3 = new DummyAutoAbility(mockCharacter3, EventType.DREW_CARD, true);
		dummy3.addCost(new PayStock(1));
		dummy3.addAction(new TakeDamage(1));
		ArrayList<AutoAbility> dummyList3 = new ArrayList<>();
		dummyList3.add(dummy3);
		doReturn(dummyList3).when(mockCharacter3).getAutoAbilities();

		doReturn(dummy, dummy3, dummy2, mockCard).when(mockReader).getChoice(anyString(), anyList());
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		stage.place(mockCharacter2, SlotType.FRONT_LEFT);
		stage.place(mockCharacter3, SlotType.FRONT_RIGHT);
		controller1.getPlayer().endPhase();
		stock.add(mockCard);
		stock.add(mockCard);
		stock.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		level.add(mockCard);
		level.add(mockCard);
		level.add(mockCard);
		dummy.setTargets(controller1, controller2);
		dummy2.setTargets(controller1, controller2);
		dummy3.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(mockCharacter2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(mockCharacter3, stage.getSlot(SlotType.FRONT_RIGHT).getCharacter());
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		assertEquals(dummy2, mockCharacter2.getAutoAbilities().get(0));
		assertEquals(1, mockCharacter3.getAutoAbilities().size());
		assertEquals(dummy3, mockCharacter3.getAutoAbilities().get(0));

		assertEquals(PlayerPhase.STAND, controller1.getPlayer().getPhase());
		
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(3, level.size());
		assertEquals(4, damage.size());
		assertEquals(3, stock.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		controller1.getPlayer().executeCommand();
		controller1.getPlayer().executeCommand();
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(mockCharacter2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(mockCharacter3, stage.getSlot(SlotType.FRONT_RIGHT).getCharacter());
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		assertEquals(dummy2, mockCharacter2.getAutoAbilities().get(0));
		assertEquals(1, mockCharacter3.getAutoAbilities().size());
		assertEquals(dummy3, mockCharacter3.getAutoAbilities().get(0));
		assertEquals(PlayerPhase.CLOCK,controller1.getPlayer().getPhase());
		assertEquals(1, hand.size());
		assertEquals(0, stock.size());
		assertEquals(4, level.size());
		assertEquals(0, damage.size());
		assertEquals(46, library.size());
		assertEquals(9, waitingRoom.size());
		assertFalse(controller1.isAlive());
	}
}
