package model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

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
import model.ability.action.PayStock;
import model.ability.action.TakeDamage;
import model.board.Board;
import model.board.DamageZone;
import model.board.Hand;
import model.board.Library;
import model.board.SlotType;
import model.board.Stage;
import model.board.Stock;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Climax;
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
		
		// Setup Dummy Ability
		dummy = new DummyPhaseAutoAbility(mockCharacter, true, PlayerPhase.DRAW, PhaseTiming.START);		
		dummy.addCost(new PayStock(1));
		dummy.addAction(new TakeDamage(1));
		dummyList = new ArrayList<>();
		dummyList.add(dummy);
		doReturn(dummyList).when(mockCharacter).getAutoAbilities();
	}
	
	@Test
	public void test1(){
		// Setup Test
		stock.add(mockCard);
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertEquals(0, hand.size());
		assertEquals(1, stock.size());
		assertEquals(0, damage.size());
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		
		
		// Perform Actions
		
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertEquals(1, hand.size());
		assertEquals(0, stock.size());
		assertEquals(1, damage.size());
		assertEquals(48, library.size());
		assertEquals(1, waitingRoom.size());

		
	}
	
}
