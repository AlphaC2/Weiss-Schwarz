package model.ability.action;

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

import controller.GameManager;
import controller.PlayerController;
import io.Reader;
import io.Writer;
import model.board.Board;
import model.board.DamageZone;
import model.board.LevelZone;
import model.board.Library;
import model.board.ResolutionZone;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Climax;
import model.exceptions.EmptyLibraryException;

public class TestTakeDamage {
	private Board board;
	private PlayerController controller;
	private static int testNumber = 0;
	private Library library;
	private ResolutionZone resolution;
	private DamageZone damage;
	private WaitingRoom waitingRoom;
	private LevelZone level;

	@Mock
	Card mockCard;

	@Mock
	Character mockCharacter;

	@Mock
	Character mockCharacter2;

	@Mock
	Climax mockClimax;

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
		library.placeTop(mockCharacter);

		// Check Preconditions
		assertEquals(51, library.size());
		assertEquals(mockCharacter, library.peek());
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(mockCharacter, damage.getCards().get(0));
		assertEquals(1, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void CancelOneDamage() {
		// Setup Test
		library.placeTop(mockClimax);

		// Check Preconditions
		assertEquals(51, library.size());
		assertEquals(mockClimax, library.peek());
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(1).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(1, waitingRoom.size());
		assertEquals(mockClimax, waitingRoom.getCards().get(0));
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void TakeThreeDamage() {
		// Setup Test
		library.placeTop(mockCharacter);
		library.placeTop(mockCharacter);
		library.placeTop(mockCharacter);

		// Check Preconditions
		assertEquals(53, library.size());
		assertEquals(mockCharacter, library.getCards().get(0));
		assertEquals(mockCharacter, library.getCards().get(1));
		assertEquals(mockCharacter, library.getCards().get(2));
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
		library.placeTop(mockClimax);
		library.placeTop(mockCharacter);
		library.placeTop(mockCharacter);

		// Check Preconditions
		assertEquals(53, library.size());
		assertEquals(mockCharacter, library.getCards().get(0));
		assertEquals(mockCharacter, library.getCards().get(1));
		assertEquals(mockClimax, library.getCards().get(2));
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());

		// Perform Actions
		new TakeDamage(3).execute(controller, controller);

		// Check Postconditions
		assertEquals(50, library.size());
		assertEquals(3, waitingRoom.size());
		assertTrue(waitingRoom.getCards().contains(mockClimax));
		assertEquals(0, damage.size());
		assertEquals(0, resolution.size());
	}

	@Test
	public void TakeDamageRefresh() {
		// Setup Test
		List<Card> cards = library.getCards();
		library.placeTop(mockCharacter);
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				fail();
				e.printStackTrace();
			}
		}
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);

		// Check Preconditions
		assertEquals(1, library.size());
		assertEquals(mockCharacter, library.peek());
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
		library.placeTop(mockClimax);
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				fail();
				e.printStackTrace();
			}
		}
		waitingRoom.add(mockClimax);
		waitingRoom.add(mockClimax);
		waitingRoom.add(mockClimax);

		// Check Preconditions
		assertEquals(1, library.size());
		assertEquals(3, waitingRoom.size());
		List<Card> waitingCards = waitingRoom.getCards();
		for (Card card : waitingCards) {
			assertEquals(card, mockClimax);
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
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCharacter);

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
		assertEquals(mockCharacter, level.getCards().get(0));
		assertEquals(0, resolution.size());
	}

	@Test
	public void TakeDamageMoreThanLevelUp() {
		// Setup Test
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCharacter);

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
		assertEquals(mockCharacter, level.getCards().get(0));
		assertEquals(0, resolution.size());
	}

	@Test
	public void CancelDamageMoreThanLevelUp() {
		// Setup Test
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCharacter);
		library.placeTop(mockClimax);
		library.placeTop(mockCard);

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
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());
		List<Card> cards = library.getCards();
		library.placeTop(mockCard);
		for (Card card : cards) {
			try {
				library.remove(card);
			} catch (EmptyLibraryException e) {
				fail();
				e.printStackTrace();
			}
		}
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		waitingRoom.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCharacter);

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
		library.add(mockCard);
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
