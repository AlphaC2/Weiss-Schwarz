package model.ability.mods;

import static org.junit.Assert.*;
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
import model.board.*;
import model.card.*;
import model.card.Character;

public class TestMods {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private Library library;
	private Hand hand;
	
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
			Card filler = DummyFactory.createCard(DummyName.BasicCharacter);
			deck.add(filler);
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
		board = controller1.getBoard();
		library = board.getLibrary();
		hand = board.getHand();
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void modsAreAdded(){
		
	}
	
	
}