package game.model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.GameManager;
import game.controller.PlayerController;
import game.io.CardXMLReader;
import game.io.Reader;
import game.io.Writer;
import game.model.ability.action.concrete.PayStock;
import game.model.ability.action.concrete.TakeDamage;
import game.model.ability.auto.AutoAbility;
import game.model.ability.auto.DummyAutoAbility;
import game.model.ability.auto.DummyPhaseAutoAbility;
import game.model.ability.auto.PhaseAutoAbility;
import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.Hand;
import game.model.board.LevelZone;
import game.model.board.Library;
import game.model.board.SlotType;
import game.model.board.Stage;
import game.model.board.Stock;
import game.model.board.WaitingRoom;
import game.model.card.Card;
import game.model.card.Character;
import game.model.gameEvent.EventType;
import game.model.player.PhaseTiming;
import game.model.player.PlayerPhase;

public class TestPhaseTransitionAbilities {
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
	private LevelZone level;
	private AutoAbility dummy;
	private Character basicCharacter;
	private Character character1;
	private Character character2;
	private Character character3;
	
	@Mock
	Reader mockReader;
	
	@Mock
	Writer mockWriter;
	
	@Before
	public void init() {
		testNumber++;
		System.out.println("\nTest Number " + testNumber);

		MockitoAnnotations.initMocks(this);
		basicCharacter = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		character1 = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		character2 = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		character3 = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(basicCharacter);
		}

		// Real Controller setup
		controller1 = new PlayerController("P1", mockReader, mockWriter);
		controller1.setDeck(deck);
		controller1.testing();
		board = controller1.getBoard();

		controller2 = new PlayerController("P2", mockReader, mockWriter);
		controller2.setDeck(deck);
		controller2.testing();
		
		// Gamemanager setup
		new GameManager(controller1, controller2);

		// Zone setup
		library = board.getLibrary();
		damage = board.getDamageZone();
		waitingRoom = board.getWaitingRoom();
		stage = board.getStage();
		hand = board.getHand();
		stock = board.getStock();
		level = board.getLevel();
		
		// Setup Dummy Ability
		dummy = new DummyPhaseAutoAbility(character1, true, PlayerPhase.DRAW, PhaseTiming.START);		
		dummy.addCost(new PayStock(1));
		dummy.addAction(new TakeDamage(1));
		
		character1.addAbility(dummy);
		doReturn(dummy).when(mockReader).getChoice(anyString(), anyList());
		
		//Basic Preconditions
		assertEquals(1, character1.getAutoAbilities().size());
		assertEquals(dummy, character1.getAutoAbilities().get(0));
	}
	
	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	
	@Test
	public void BeginningOfDrawPhaseTrigger(){
		// Setup Test
		controller1.getPlayer().endPhase();
		stage.place(character1, SlotType.FRONT_CENTER);
		stock.add(basicCharacter);
		dummy.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(character1, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(PlayerPhase.STAND ,controller1.getPlayer().getPhase());
		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, damage.size());
		assertEquals(1, stock.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		controller1.getPlayer().endPhase();
		
		// Check Postconditions
		assertEquals(character1, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
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
		PhaseAutoAbility dummy2 = new DummyPhaseAutoAbility(character2, true, PlayerPhase.STAND, PhaseTiming.END);
		dummy2.addCost(new PayStock(1));
		dummy2.addAction(new TakeDamage(1));
		character2.addAbility(dummy2);
		doReturn(dummy2, dummy).when(mockReader).getChoice(anyString(), anyList());
		
		controller1.getPlayer().endPhase();
		stage.place(character1, SlotType.FRONT_CENTER);
		stage.place(character2, SlotType.FRONT_LEFT);
		stock.add(basicCharacter);
		stock.add(basicCharacter);
		dummy.setTargets(controller1, controller2);
		dummy2.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(character1, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(character2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(1, character2.getAutoAbilities().size());
		assertEquals(dummy2, character2.getAutoAbilities().get(0));
		assertEquals(PlayerPhase.STAND,controller1.getPlayer().getPhase());

		assertEquals(0, hand.size());
		assertEquals(50, library.size());
		assertEquals(0, damage.size());
		assertEquals(2, stock.size());
		assertEquals(0, waitingRoom.size());
		
		// Perform Actions
		controller1.getPlayer().endPhase();
		
		// Check Postconditions
		assertEquals(character1, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(character2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(1, character2.getAutoAbilities().size());
		assertEquals(dummy2, character2.getAutoAbilities().get(0));
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
		PhaseAutoAbility dummy2 = new DummyPhaseAutoAbility(character2, true, PlayerPhase.DRAW, PhaseTiming.END);
		dummy2.addCost(new PayStock(1));
		dummy2.addAction(new TakeDamage(1));
		character2.addAbility(dummy2);
		
		//Ability 3
		AutoAbility dummy3 = new DummyAutoAbility(character3, EventType.DREW_CARD, true);
		dummy3.addCost(new PayStock(1));
		dummy3.addAction(new TakeDamage(1));
		character3.addAbility(dummy3);
		
		doReturn(dummy, dummy3, dummy2, basicCharacter).when(mockReader).getChoice(anyString(), anyList());
		stage.place(character1, SlotType.FRONT_CENTER);
		stage.place(character2, SlotType.FRONT_LEFT);
		stage.place(character3, SlotType.FRONT_RIGHT);
		controller1.getPlayer().endPhase();
		stock.add(basicCharacter);
		stock.add(basicCharacter);
		stock.add(basicCharacter);
		damage.add(basicCharacter);
		damage.add(basicCharacter);
		damage.add(basicCharacter);
		damage.add(basicCharacter);
		level.add(basicCharacter);
		level.add(basicCharacter);
		level.add(basicCharacter);
		dummy.setTargets(controller1, controller2);
		dummy2.setTargets(controller1, controller2);
		dummy3.setTargets(controller1, controller2);
		
		// Check Preconditions
		assertEquals(character1, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(character2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(character3, stage.getSlot(SlotType.FRONT_RIGHT).getCharacter());
		assertEquals(1, character2.getAutoAbilities().size());
		assertEquals(dummy2, character2.getAutoAbilities().get(0));
		assertEquals(1, character3.getAutoAbilities().size());
		assertEquals(dummy3, character3.getAutoAbilities().get(0));

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
		assertEquals(character1, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(character2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter());
		assertEquals(character3, stage.getSlot(SlotType.FRONT_RIGHT).getCharacter());
		assertEquals(1, character2.getAutoAbilities().size());
		assertEquals(dummy2, character2.getAutoAbilities().get(0));
		assertEquals(1, character3.getAutoAbilities().size());
		assertEquals(dummy3, character3.getAutoAbilities().get(0));
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
