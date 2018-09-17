package model.ability.auto;

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
import model.ability.action.DrawToHand;
import model.board.Board;
import model.board.DamageZone;
import model.board.Hand;
import model.board.LevelZone;
import model.board.Library;
import model.board.ResolutionZone;
import model.board.SlotType;
import model.board.Stage;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Climax;
import model.gameEvent.EventType;
import model.player.Player;

public class TestDummyAutoAbility {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private Library library;
	private ResolutionZone resolution;
	private DamageZone damage;
	private WaitingRoom waitingRoom;
	private LevelZone level;
	private Stage stage;
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

		// Real Controller setup
		controller1 = new ConsoleController("Real Player");
		controller1.setReader(mockReader);
		controller1.setDeck(deck);
		board = controller1.getBoard();
		
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
		library = board.getLibrary();
		resolution = board.getResolutionZone();
		damage = board.getDamageZone();
		waitingRoom = board.getWaitingRoom();
		level = board.getLevel();
		stage = board.getStage();
		hand = board.getHand();
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void SelfDrawSelfTakeDamage(){
		// Setup Test
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(mockCharacter, EventType.DREW_CARD, true);
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
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());

		// Perform Actions
		new DrawToHand().execute(controller1, null);
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(1, damage.size());
		assertEquals(6, hand.size());
		assertEquals(48, library.size());
	}
	
	@Test
	public void SelfDrawOpponentTakeDamage(){
		// Setup Test
		controller2.getBoard().getStage().place(mockCharacter, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(mockCharacter, EventType.DREW_CARD, false);
		List<AutoAbility> dummyList = new ArrayList<>();
		dummyList.add(dummy);
		when(mockCharacter.getAutoAbilities()).thenReturn(dummyList);
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);

		// Check Preconditions
		assertEquals(mockCharacter, controller2.getBoard().getStage().getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(0, controller2.getBoard().getHand().size());
		assertEquals(50, controller2.getBoard().getLibrary().size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, null);
		
		// Check Postconditions
		assertEquals(mockCharacter, controller2.getBoard().getStage().getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(49, library.size());
		assertEquals(0, damage.size());
		assertEquals(1, hand.size());
		
		assertEquals(1, controller2.getBoard().getDamageZone().size());
		assertEquals(49, controller2.getBoard().getLibrary().size());
	}
	
	@Test
	public void MultipleSelfDrawSelfTakeDamage(){
		// Setup Test
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(mockCharacter, EventType.DREW_CARD, true);
		List<AutoAbility> dummyList = new ArrayList<>();
		dummyList.add(dummy);
		when(mockCharacter.getAutoAbilities()).thenReturn(dummyList);
		
		stage.place(mockCharacter2, SlotType.FRONT_LEFT);
		AutoAbility dummy2 = new DummyAutoAbility(mockCharacter2, EventType.DREW_CARD, true);
		List<AutoAbility> dummyList2 = new ArrayList<>();
		dummyList2.add(dummy2);
		when(mockCharacter2.getAutoAbilities()).thenReturn(dummyList2);
		
		doReturn(dummy, dummy2).when(mockReader).getChoice(anyString(), anyList());
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));

		assertEquals(mockCharacter2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		assertEquals(dummy2, mockCharacter2.getAutoAbilities().get(0));
		
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());

		// Perform Actions
		new DrawToHand().execute(controller1, null);
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		
		assertEquals(mockCharacter2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		
		assertEquals(2, damage.size());
		assertEquals(6, hand.size());
		assertEquals(47, library.size());
	}
	
	@Test
	public void P1DrawsTriggerBothTakeDamage(){
		// Setup Test
		stage.place(mockCharacter, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(mockCharacter, EventType.DREW_CARD, true);
		List<AutoAbility> dummyList = new ArrayList<>();
		dummyList.add(dummy);
		when(mockCharacter.getAutoAbilities()).thenReturn(dummyList);
		
		controller2.getBoard().getStage().place(mockCharacter2, SlotType.FRONT_LEFT);
		AutoAbility dummy2 = new DummyAutoAbility(mockCharacter2, EventType.DREW_CARD, false);
		List<AutoAbility> dummyList2 = new ArrayList<>();
		dummyList2.add(dummy2);
		when(mockCharacter2.getAutoAbilities()).thenReturn(dummyList2);
		
		doReturn(dummy, dummy2).when(mockReader).getChoice(anyString(), anyList());
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		hand.add(mockCard);
		
		// Check Preconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(dummy, mockCharacter.getAutoAbilities().get(0));
		
		assertEquals(mockCharacter2, controller2.getBoard().getStage().getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		assertEquals(dummy2, mockCharacter2.getAutoAbilities().get(0));
		
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		
		assertEquals(0, controller2.getBoard().getDamageZone().size());
		assertEquals(50, controller2.getBoard().getLibrary().size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, null);
		
		// Check Postconditions
		assertEquals(mockCharacter, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, mockCharacter.getAutoAbilities().size());
		assertEquals(1, damage.size());
		assertEquals(6, hand.size());
		assertEquals(48, library.size());
		
		assertEquals(mockCharacter2, controller2.getBoard().getStage().getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, mockCharacter2.getAutoAbilities().size());
		assertEquals(1, controller2.getBoard().getDamageZone().size());
		assertEquals(49, controller2.getBoard().getLibrary().size());
		
	}

}
