package game.model.ability.mods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.GameManager;
import game.controller.PlayerController;
import game.io.Reader;
import game.io.Writer;
import game.model.ability.action.concrete.GiveModToHand;
import game.model.ability.action.condition.Self;
import game.model.ability.mods.CardMod;
import game.model.ability.mods.ModType;
import game.model.ability.mods.NumberMod;
import game.model.board.Board;
import game.model.board.Hand;
import game.model.card.Card;
import game.model.card.DummyFactory;
import game.model.card.DummyName;
import game.model.player.PhaseTiming;
import game.model.player.PlayerPhase;
import game.model.player.PlayerPhaseTiming;

public class TestMods {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private Hand hand;
	private CardMod<Integer> mod;
	private Card target;
	
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
		controller1.testing();
		board = controller1.getBoard();
		
		controller2 = new PlayerController("P2", mockReader, mockWriter);
		controller2.setDeck(deck);
		controller2.testing();
		
		// Gamemanager setup
		new GameManager(controller1, controller2);
		
		// Zone setup
		board = controller1.getBoard();
		hand = board.getHand();
		
		//Card Setup
		target = DummyFactory.createCard(DummyName.LevelOneCharacter);
		hand.add(target);
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void modsAreAdded(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.DRAW, PhaseTiming.END);
		mod = new NumberMod(ModType.LEVEL, -1);
		
		// Check Preconditions
		assertNotNull(target);
		assertEquals(1, target.getLevel());
		assertEquals(1, hand.size());
		assertEquals(target, hand.getCards().get(0));
		assertEquals(PlayerPhase.OPPONENTS_TURN, controller1.getPlayer().getPhase());
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(mod, ppt);
		action.addCondition(new Self(target));
		action.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(0, target.getLevel());
		assertEquals(PlayerPhase.OPPONENTS_TURN, controller1.getPlayer().getPhase());
		
		// Perform Actions
		controller1.getPlayer().endPhase();
		
		// Check PostConditions
		assertEquals(PlayerPhase.STAND, controller1.getPlayer().getPhase());
		assertEquals(0, target.getLevel());

		// Perform Actions
		controller1.getPlayer().endPhase();
		
		// Check PostConditions
		assertEquals(PlayerPhase.DRAW, controller1.getPlayer().getPhase());
		assertEquals(0, target.getLevel());
		
		// Perform Actions
		controller1.getPlayer().endPhase();
		
		// Check PostConditions
		assertEquals(PlayerPhase.CLOCK, controller1.getPlayer().getPhase());
		assertEquals(1, target.getLevel());
		
	}
	
	
}