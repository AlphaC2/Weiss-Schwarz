package game.model.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import game.model.card.Card;
import game.model.card.Character;
import game.model.card.DummyFactory;
import game.model.card.DummyName;

public class TestRestrictedBoard {
	private Board board;
	private Hand hand;
	private Stage stage;
	private WaitingRoom waitingRoom;
	private Library library;
	private ResolutionZone resolution;
	private LevelZone level;
	private DamageZone damage;
	private MemoryZone memory;
	private Stock stock;
	private int testNumber = 0;
	private List<Card> cards = new ArrayList<>();
	
	@Before
	public void init() {
		testNumber++;
		System.out.println("\nTest Number " + testNumber);

		MockitoAnnotations.initMocks(this);

		for (int i = 0; i < 50; i++) {
			cards.add(DummyFactory.createCard(DummyName.BasicCharacter));
		}

		board = new Board();
		hand = board.getHand();
		stage = board.getStage();
		waitingRoom = board.getWaitingRoom();
		library = board.getLibrary();
		resolution = board.getResolutionZone();
		level = board.getLevel();
		damage = board.getDamageZone();
		memory = board.getMemoryZone();
		stock = board.getStock();
	}
	
	@Test
	public void dupe(){
		// Setup Test
		assertEquals(50, cards.size());
		hand.add(cards.get(0));
		stage.place((Character)cards.get(1), SlotType.FRONT_CENTER);
		waitingRoom.add(cards.get(2));
		library.add(cards.get(3));
		resolution.add(cards.get(4));
		level.add(cards.get(5));
		damage.add(cards.get(6));
		memory.add(cards.get(7));
		stock.add(cards.get(8));
		
		
		// Check Preconditions
		assertEquals(1, hand.size());
		assertEquals(1, waitingRoom.size());
		assertEquals(1, library.size());
		assertEquals(1, resolution.size());
		assertEquals(1, level.size());
		assertEquals(1, damage.size());
		assertEquals(1, memory.size());
		assertEquals(1, stock.size());

		assertEquals(cards.get(0), hand.getCards().get(0));
		assertEquals(cards.get(1), stage.getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(cards.get(2), waitingRoom.getCards().get(0));
		assertEquals(cards.get(3), library.getCards().get(0));
		assertEquals(cards.get(4), resolution.getCards().get(0));
		assertEquals(cards.get(5), level.getCards().get(0));
		assertEquals(cards.get(6), damage.getCards().get(0));
		assertEquals(cards.get(7), memory.getCards().get(0));
		assertEquals(cards.get(8), stock.getCards().get(0));

		// Perform Actions
		Board newBoard = board.toRestrictedBoard();

		// Check Postconditions
		assertEquals(1, newBoard.getHand().size());
		assertEquals(1, newBoard.getWaitingRoom().size());
		assertEquals(1, newBoard.getLibrary().size());
		assertEquals(1, newBoard.getResolutionZone().size());
		assertEquals(1, newBoard.getLevel().size());
		assertEquals(1, newBoard.getDamageZone().size());
		assertEquals(1, newBoard.getMemoryZone().size());
		assertEquals(1, newBoard.getStock().size());
		
		assertNull(newBoard.getHand().getCards().get(0));
		assertEquals(cards.get(1), newBoard.getStage().getSlot(SlotType.FRONT_CENTER).getCharacter());
		assertEquals(cards.get(2), newBoard.getWaitingRoom().getCards().get(0));
		assertNull(newBoard.getLibrary().getCards().get(0));
		assertEquals(cards.get(4), newBoard.getResolutionZone().getCards().get(0));
		assertEquals(cards.get(5), newBoard.getLevel().getCards().get(0));
		assertEquals(cards.get(6), newBoard.getDamageZone().getCards().get(0));
		assertEquals(cards.get(7), newBoard.getMemoryZone().getCards().get(0));
		assertNull(newBoard.getStock().getCards().get(0));
	}
	
	
}
