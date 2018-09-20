package model.ability.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.GameManager;
import controller.PlayerController;
import io.CardXMLReader;
import io.Reader;
import io.Writer;
import model.ability.mods.CardMod;
import model.ability.mods.ModType;
import model.ability.mods.NumberMod;
import model.board.Board;
import model.board.Hand;
import model.board.Library;
import model.board.WaitingRoom;
import model.card.Card;
import model.card.Character;
import model.card.Climax;
import model.card.Event;
import model.player.PhaseTiming;
import model.player.PlayerPhase;
import model.player.PlayerPhaseTiming;

public class TestGiveModToHand {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private Library library;
	private WaitingRoom waitingRoom;
	private Hand hand;
	private CardMod mod;
	private Card target;
	
	@Mock
	Card mockCard;

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
		waitingRoom = board.getWaitingRoom();
		hand = board.getHand();
		
		//Mod setup
		mod = new NumberMod(ModType.LEVEL, -1);
		
		// Target setup
		target = CardXMLReader.read("CardData\\AB\\W11\\AB-W11-101.xml");
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void CardGetsMod(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.END, PhaseTiming.END);
		
		// Check Preconditions
		assertNotNull(target);
		assertEquals(1, target.getLevel());
		
		
		// Perform Actions
		Action action = new GiveModToHand(mod, ppt);
//		action.addCondition(new );
		//action.execute(controller1, controller2);
		
		// Check Postconditions
//		assertEquals(0, target.getLevel());
	}
	
}
