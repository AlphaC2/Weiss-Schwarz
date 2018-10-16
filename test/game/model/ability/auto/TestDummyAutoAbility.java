package game.model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
import game.model.ability.action.concrete.DrawToHand;
import game.model.ability.action.concrete.PlaceInDamageFromLibrary;
import game.model.ability.auto.AutoAbility;
import game.model.ability.auto.DummyAutoAbility;
import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.Hand;
import game.model.board.Library;
import game.model.board.SlotType;
import game.model.board.Stage;
import game.model.card.Card;
import game.model.card.Character;
import game.model.card.DummyFactory;
import game.model.card.DummyName;
import game.model.gameEvent.EventType;

public class TestDummyAutoAbility {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private String path = "CardData\\DummySet\\";
	private Library library;
	private DamageZone damage;
	private Stage stage;
	private Hand hand;
	private Character character;
	private Character character2;
	private Card dummyCard;
	
	@Mock
	Reader mockReader;

	@Mock
	Writer mockWriter;
	
	@Before
	public void init() {
		testNumber++;
		System.out.println("\nTest Number " + testNumber);

		MockitoAnnotations.initMocks(this);
		dummyCard = DummyFactory.createCard(DummyName.BasicCharacter);
		character = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		character2 = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(dummyCard);
		}

		// Real Controller setup
		controller1 = new PlayerController("Real Player", mockReader, mockWriter);
		controller1.setDeck(deck);
		controller1.testing();
		board = controller1.getBoard();
		
		controller2 = new PlayerController("Real Player2", mockReader, mockWriter);
		controller2.setDeck(deck);
		controller2.testing();
		
		// Gamemanager setup
		new GameManager(controller1, controller2);
		
		// Zone setup
		library = board.getLibrary();
		damage = board.getDamageZone();
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
		stage.place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, true);
		character.addAbility(dummy);
		dummy.addAction(new PlaceInDamageFromLibrary());
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());

		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(1, damage.size());
		assertEquals(6, hand.size());
		assertEquals(48, library.size());
	}
	
	@Test
	public void SelfDrawOpponentTakeDamage(){
		// Setup Test
		controller2.getBoard().getStage().place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, false);
		character.addAbility(dummy);
		dummy.addAction(new PlaceInDamageFromLibrary());
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);

		// Check Preconditions
		assertEquals(character, controller2.getBoard().getStage().getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(0, controller2.getBoard().getHand().size());
		assertEquals(50, controller2.getBoard().getLibrary().size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, controller2.getBoard().getStage().getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(49, library.size());
		assertEquals(0, damage.size());
		assertEquals(1, hand.size());
		
		assertEquals(1, controller2.getBoard().getDamageZone().size());
		assertEquals(49, controller2.getBoard().getLibrary().size());
	}
	
	@Test
	public void MultipleSelfDrawSelfTakeDamage(){
		// Setup Test
		stage.place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, true);
		character.addAbility(dummy);
		dummy.addAction(new PlaceInDamageFromLibrary());
		
		stage.place(character2, SlotType.FRONT_LEFT);
		AutoAbility dummy2 = new DummyAutoAbility(character2, EventType.DREW_CARD, true);
		character2.addAbility(dummy2);
		dummy2.addAction(new PlaceInDamageFromLibrary());
		
		doReturn(dummy, dummy2).when(mockReader).getChoice(anyString(), anyList());
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));

		assertEquals(character2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, character2.getAutoAbilities().size());
		assertEquals(dummy2, character2.getAutoAbilities().get(0));
		
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());

		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		
		assertEquals(character2, stage.getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, character2.getAutoAbilities().size());
		
		assertEquals(2, damage.size());
		assertEquals(6, hand.size());
		assertEquals(47, library.size());
	}
	
	@Test
	public void P1DrawsTriggerBothTakeDamage(){
		// Setup Test
		stage.place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, true);
		character.addAbility(dummy);
		dummy.addAction(new PlaceInDamageFromLibrary());
		
		controller2.getBoard().getStage().place(character2, SlotType.FRONT_LEFT);
		AutoAbility dummy2 = new DummyAutoAbility(character2, EventType.DREW_CARD, false);
		character2.addAbility(dummy2);
		dummy2.addAction(new PlaceInDamageFromLibrary());
		
		doReturn(dummy, dummy2).when(mockReader).getChoice(anyString(), anyList());
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		
		assertEquals(character2, controller2.getBoard().getStage().getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, character2.getAutoAbilities().size());
		assertEquals(dummy2, character2.getAutoAbilities().get(0));
		
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		
		assertEquals(0, controller2.getBoard().getDamageZone().size());
		assertEquals(50, controller2.getBoard().getLibrary().size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(1, damage.size());
		assertEquals(6, hand.size());
		assertEquals(48, library.size());
		
		assertEquals(character2, controller2.getBoard().getStage().getSlot(SlotType.FRONT_LEFT).getCharacter() );
		assertEquals(1, character2.getAutoAbilities().size());
		assertEquals(1, controller2.getBoard().getDamageZone().size());
		assertEquals(49, controller2.getBoard().getLibrary().size());
		
	}

	@Test
	public void SelfDrawSelfTakeOptionalDamage(){
		// Setup Test
		stage.place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, true, true);
		character.addAbility(dummy);
		dummy.addAction(new PlaceInDamageFromLibrary());
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);
		when(mockReader.getChoice(anyString())).thenReturn(true);
		
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());

		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(1, damage.size());
		assertEquals(6, hand.size());
		assertEquals(48, library.size());
	}
	
	@Test
	public void SelfDrawSelfTakeNoOptionalDamage(){
		// Setup Test
		stage.place(character, SlotType.FRONT_CENTER);
		AutoAbility dummy = new DummyAutoAbility(character, EventType.DREW_CARD, true, true);
		character.addAbility(dummy);
		
		dummy.addAction(new PlaceInDamageFromLibrary());
		when(mockReader.getChoice(anyString(), anyList())).thenReturn(dummy);
		when(mockReader.getChoice(anyString())).thenReturn(false);
		
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		hand.add(dummyCard);
		
		// Check Preconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(dummy, character.getAutoAbilities().get(0));
		assertEquals(0, damage.size());
		assertEquals(5, hand.size());
		assertEquals(50, library.size());
		
		// Perform Actions
		new DrawToHand().execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(character, stage.getSlot(SlotType.FRONT_CENTER).getCharacter() );
		assertEquals(1, character.getAutoAbilities().size());
		assertEquals(0, damage.size());
		assertEquals(6, hand.size());
		assertEquals(49, library.size());
	}
	
}
