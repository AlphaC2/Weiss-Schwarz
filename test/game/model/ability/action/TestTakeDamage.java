package game.model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
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
import game.model.ability.action.TakeDamage;
import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.LevelZone;
import game.model.board.Library;
import game.model.board.ResolutionZone;
import game.model.board.WaitingRoom;
import game.model.card.Card;
import game.model.card.Character;
import game.model.card.Climax;
import game.model.exceptions.EmptyLibraryException;

public class TestTakeDamage {
	private Board board;
	private PlayerController controller;
	private static int testNumber = 0;
	private Library library;
	private ResolutionZone resolution;
	private DamageZone damage;
	private WaitingRoom waitingRoom;
	private LevelZone level;
	private Climax climax;
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
		
		//Card setup
		character = (Character) CardXMLReader.read("CardData\\DummySet\\BasicCharacter.xml");
		climax = (Climax) CardXMLReader.read("CardData\\DummySet\\DummyClimax.xml");
		
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(character);
		}

		// Real Controller setup
		controller = new PlayerController("P1", mockReader, mockWriter);
		controller.setDeck(deck);
		board = controller.getBoard();

		// Gamemanager setup
		new GameManager(controller, controller);
		
		// Zone setup
		library = board.getLibrary();
		resolution = board.getResolutionZone();
		damage = board.getDamageZone();
		waitingRoom = board.getWaitingRoom();
		level = board.getLevel();

	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void TakeOneDamage() {
		// Setup Test
		library.placeTop(character);

		// Check Preconditions
		assertEquals(51, library.size());
		assertEquals(character, library.peek());
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(character, damage.getCards().get(0));
		assertEquals(1, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void CancelOneDamage() {
		// Setup Test
		library.placeTop(climax);

		// Check Preconditions
		assertEquals(51, library.size());
		assertEquals(climax, library.peek());
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(1, waitingRoom.size());
		assertEquals(climax, waitingRoom.getCards().get(0));
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void TakeThreeDamage() {
		// Setup Test
		library.placeTop(character);
		library.placeTop(character);
		library.placeTop(character);

		// Check Preconditions
		assertEquals(53, library.size());
		assertEquals(character, library.getCards().get(0));
		assertEquals(character, library.getCards().get(1));
		assertEquals(character, library.getCards().get(2));
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(3).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(0, waitingRoom.size());
		assertEquals(3, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void CancelThreeDamage() {
		// Setup Test
		library.placeTop(climax);
		library.placeTop(character);
		library.placeTop(character);

		// Check Preconditions
		assertEquals(53, library.size());
		assertEquals(character, library.getCards().get(0));
		assertEquals(character, library.getCards().get(1));
		assertEquals(climax, library.getCards().get(2));
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(3).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(3, waitingRoom.size());
		assertTrue(waitingRoom.getCards().contains(climax));
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void TakeDamageRefresh() {
		// Setup Test
		List<Card> cards = library.getCards();
		library.placeTop(character);
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				fail();
				e.printStackTrace();
			}
		}
		waitingRoom.add(character);
		waitingRoom.add(character);

		// Check Preconditions
		assertEquals(1, library.size());
		assertEquals(character, library.peek());
		assertEquals(0, damage.size());
		assertEquals(2, waitingRoom.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(0, waitingRoom.size());
		assertEquals(2, damage.size());
		assertEquals(1, library.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void RefreshDamageWithClimax() {
		// Setup Test
		List<Card> cards = library.getCards();
		library.placeTop(climax);
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				fail();
				e.printStackTrace();
			}
		}
		waitingRoom.add(climax);
		waitingRoom.add(climax);
		waitingRoom.add(climax);

		// Check Preconditions
		assertEquals(1, library.size());
		assertEquals(3, waitingRoom.size());
		List<Card> waitingCards = waitingRoom.getCards();
		for (Card card : waitingCards) {
			assertEquals(card, climax);
		}
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(2, library.size());
		assertEquals(1, waitingRoom.size());
		assertEquals(1, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void TakeDamageLevelUp() {
		// Setup Test
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);

		// Check Preconditions
		assertEquals(50, library.size());
		assertEquals(6, damage.size());
		assertEquals(0, level.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(49, library.size());
		assertEquals(0, damage.size());
		assertEquals(1, level.size());
		assertEquals(6, board.getWaitingRoom().size());
		assertEquals(character, level.getCards().get(0));
		assertEquals(0, resolution.size());
	}

	@Test
	public void TakeDamageMoreThanLevelUp() {
		// Setup Test
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);

		// Check Preconditions
		assertEquals(50, library.size());
		assertEquals(6, damage.size());
		assertEquals(0, level.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(2).execute(controller, controller);

		// Check Postconditions
		assertEquals(48, library.size());
		assertEquals(1, damage.size());
		assertEquals(1, level.size());
		assertEquals(6, board.getWaitingRoom().size());
		assertEquals(character, level.getCards().get(0));
		assertEquals(0, resolution.size());
	}

	@Test
	public void CancelDamageMoreThanLevelUp() {
		// Setup Test
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		library.placeTop(climax);
		library.placeTop(character);

		// Check Preconditions
		assertEquals(52, library.size());
		assertEquals(6, damage.size());
		assertEquals(0, level.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(2).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(6, damage.size());
		assertEquals(0, level.size());
		assertEquals(2, waitingRoom.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void RefreshLevelUp() {
		// Setup Test
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		List<Card> cards = library.getCards();
		library.placeTop(character);
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				fail();
				e.printStackTrace();
			}
		}
		waitingRoom.add(character);
		waitingRoom.add(character);
		waitingRoom.add(character);
		waitingRoom.add(character);
		waitingRoom.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);

		// Check Preconditions
		assertEquals(1, library.size());
		assertEquals(5, waitingRoom.size());
		assertEquals(6, damage.size());
		assertEquals(0, level.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(4, library.size());
		assertEquals(6, waitingRoom.size());
		assertEquals(1, damage.size());
		assertEquals(1, level.size());
	}

	@Test
	public void RefreshWithEmptyWaitingRoom() {
		// Setup Test
		List<Card> cards = library.getCards();
		library.add(character);
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				fail();
				e.printStackTrace();
			}
		}

		// Check Preconditions
		assertEquals(0, waitingRoom.size());
		assertEquals(1, library.size());
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());
		
		// Perform Actions
		new TakeDamage(1).execute(controller, controller);
		
		// Check Postconditions
		assertEquals(1, resolution.size());
		assertEquals(0, waitingRoom.size());
		assertEquals(0, library.size());
		assertFalse(controller.isAlive());
	}
}
