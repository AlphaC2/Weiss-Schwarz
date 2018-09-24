package game.model.ability.action;

import static org.junit.Assert.*;

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
import game.model.ability.action.GiveModToHand;
import game.model.ability.action.condition.Self;
import game.model.ability.continuous.ContinuousAbility;
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

import static org.mockito.Mockito.*;

public class TestGiveModToHand {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private Hand hand;
	private CardMod<Integer> mod;
	@SuppressWarnings("rawtypes")
	private List<CardMod> modList;
	private Card target;
	private Card dummy;
	private Card dummy2;
	
	@Mock
	Card mockCard;
	
	@Mock
	ContinuousAbility ability;
	
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
		hand = board.getHand();
		
		// Target setup
		modList = new ArrayList<>();
		target = DummyFactory.createCard(DummyName.LevelOneCharacter);
		dummy = DummyFactory.createCard(DummyName.LevelOneCharacter);
		dummy2 = DummyFactory.createCard(DummyName.LevelOneCharacter);
		hand.add(target);
	}

	// Setup Test
	// Check Preconditions
	// Perform Actions
	// Check Postconditions
	@Test
	public void CardGetstimingMod(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.END, PhaseTiming.END);
		mod = new NumberMod(ModType.LEVEL, -1);
		
		// Check Preconditions
		assertNotNull(target);
		assertEquals(1, target.getLevel());
		assertEquals(1, hand.size());
		assertEquals(target, hand.getCards().get(0));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(mod, ppt);
		action.addCondition(new Self(target));
		action.execute(controller1, controller2);
		
		
		// Check Postconditions
		assertEquals(0, target.getLevel());
	}
	
	@Test
	public void OnlyCardGetsTimingMod(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.END, PhaseTiming.END);
		mod = new NumberMod(ModType.LEVEL, -1);
		hand.add(dummy);
		
		// Check Preconditions
		assertNotNull(target);
		assertNotNull(dummy);
		assertEquals(1, target.getLevel());
		assertEquals(1, dummy.getLevel());
		assertEquals(2, hand.size());
		assertEquals(target, hand.getCards().get(0));
		assertEquals(dummy, hand.getCards().get(1));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(mod, ppt);
		action.addCondition(new Self(target));
		action.execute(controller1, controller2);
		
		
		// Check Postconditions
		assertEquals(0, target.getLevel());
		assertEquals(1, dummy.getLevel());
	}
	
	@Test
	public void AllOtherCardGetsTimingMod(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.END, PhaseTiming.END);
		mod = new NumberMod(ModType.LEVEL, -1);
		hand.add(dummy);
		hand.add(dummy2);
		
		// Check Preconditions
		assertNotNull(target);
		assertNotNull(dummy);
		assertNotNull(dummy2);
		assertEquals(1, target.getLevel());
		assertEquals(1, dummy.getLevel());
		assertEquals(1, dummy2.getLevel());
		assertEquals(3, hand.size());
		assertEquals(target, hand.getCards().get(0));
		assertEquals(dummy, hand.getCards().get(1));
		assertEquals(dummy2, hand.getCards().get(2));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(mod, ppt);
		action.addCondition(new Self(target, true));
		action.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(1, target.getLevel());
		assertEquals(0, dummy.getLevel());
		assertEquals(0, dummy2.getLevel());
	}
	
	@Test
	public void SelfCardGetsContinuousMod(){
		// Setup Test
		doReturn(true, false).when(ability).isEnabled();
		mod = new NumberMod(ModType.LEVEL, -1);
		hand.add(dummy);
		
		// Check Preconditions
		assertNotNull(target);
		assertNotNull(dummy);
		assertEquals(1, target.getLevel());
		assertEquals(1, dummy.getLevel());
		assertEquals(2, hand.size());
		assertEquals(target, hand.getCards().get(0));
		assertEquals(dummy, hand.getCards().get(1));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(mod, ability);
		action.addCondition(new Self(target));
		action.execute(controller1, controller2);
		
		
		// Check Postconditions		
		assertEquals(0, target.getLevel());
		assertEquals(1, dummy.getLevel());
		
		assertEquals(1, target.getLevel());
		assertEquals(1, dummy.getLevel());
	}
	
	@Test
	public void OtherCardsGetsContinuousMod(){
		// Setup Test
		doReturn(true, true, false, false).when(ability).isEnabled();
		mod = new NumberMod(ModType.LEVEL, -1);
		hand.add(dummy);
		hand.add(dummy2);
		
		// Check Preconditions
		assertNotNull(target);
		assertNotNull(dummy);
		assertNotNull(dummy2);
		assertEquals(1, target.getLevel());
		assertEquals(1, dummy.getLevel());
		assertEquals(1, dummy2.getLevel());
		assertEquals(3, hand.size());
		assertEquals(target, hand.getCards().get(0));
		assertEquals(dummy, hand.getCards().get(1));
		assertEquals(dummy2, hand.getCards().get(2));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(mod, ability);
		action.addCondition(new Self(target, true));
		action.execute(controller1, controller2);
		
		// Check Postconditions		
		assertEquals(1, target.getLevel());
		assertEquals(0, dummy.getLevel());
		assertEquals(0, dummy2.getLevel());
		
		assertEquals(1, target.getLevel());
		assertEquals(1, dummy.getLevel());
		assertEquals(1, dummy2.getLevel());
	}
	
	@Test
	public void CardGetsMultipleTimingMod(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.END, PhaseTiming.END);
		modList.add(new NumberMod(ModType.LEVEL, -1));
		modList.add(new NumberMod(ModType.LEVEL, +2));
		modList.add(new NumberMod(ModType.LEVEL, 32, true));
		modList.add(new NumberMod(ModType.LEVEL, -16));
		
		// Check Preconditions
		assertNotNull(target);
		assertEquals(1, target.getLevel());
		assertEquals(1, hand.size());
		assertEquals(target, hand.getCards().get(0));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(modList, ppt);
		action.addCondition(new Self(target));
		action.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(16, target.getLevel());
	}
	
	@Test
	public void CardGetsTimingModBelowZero(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.END, PhaseTiming.END);
		modList.add(new NumberMod(ModType.LEVEL, -2));
		
		// Check Preconditions
		assertNotNull(target);
		assertEquals(1, target.getLevel());
		assertEquals(1, hand.size());
		assertEquals(target, hand.getCards().get(0));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(modList, ppt);
		action.addCondition(new Self(target));
		action.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(0, target.getLevel());
	}
	
	@Test
	public void CardGetsMultipleTimingModBelowZero(){
		// Setup Test
		PlayerPhaseTiming ppt = new PlayerPhaseTiming(PlayerPhase.END, PhaseTiming.END);
		modList.add(new NumberMod(ModType.LEVEL, -1));
		modList.add(new NumberMod(ModType.LEVEL, +2));
		modList.add(new NumberMod(ModType.LEVEL, -5));
		modList.add(new NumberMod(ModType.LEVEL, +4));
		
		// Check Preconditions
		assertNotNull(target);
		assertEquals(1, target.getLevel());
		assertEquals(1, hand.size());
		assertEquals(target, hand.getCards().get(0));
		
		// Perform Actions
		GiveModToHand action = new GiveModToHand(modList, ppt);
		action.addCondition(new Self(target));
		action.execute(controller1, controller2);
		
		// Check Postconditions
		assertEquals(4, target.getLevel());
	}
	
}
