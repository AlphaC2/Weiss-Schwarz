package game.model.ability.action.concrete;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

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
import game.model.ability.action.concrete.ResolutionToDamage;
import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.LevelZone;
import game.model.board.ResolutionZone;
import game.model.board.WaitingRoom;
import game.model.card.Card;
import game.model.card.Character;

public class TestResolutionToDamage {
	private Board board;
	private PlayerController controller;
	private static int testNumber = 0;
	private ResolutionZone resolution;
	private DamageZone damage;
	private WaitingRoom waitingRoom;
	private LevelZone level;
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
		resolution = board.getResolutionZone();
		damage = board.getDamageZone();
		waitingRoom = board.getWaitingRoom();
		level = board.getLevel();
	}
	
	//Setup Test
	//Check Preconditions
	//Perform Actions
	//Check Postconditions
	@Test
	public void OneInResolutionNoneInDamage(){
		//Setup Test
		resolution.add(character);
		
		//Check Preconditions
		assertEquals(1, resolution.size());
		assertEquals(0, damage.size());
		
		//Perform Actions
		new ResolutionToDamage().execute(controller, controller);
		
		//Check Postconditions
		assertEquals(0, resolution.size());
		assertEquals(1, damage.size());

	}
	
	@Test
	public void MultipleInResolutionNoneInDamage(){
		//Setup Test
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		
		//Check Preconditions
		assertEquals(3, resolution.size());
		assertEquals(0, damage.size());
		
		//Perform Actions
		new ResolutionToDamage().execute(controller, controller);
		
		//Check Postconditions
		assertEquals(0, resolution.size());
		assertEquals(3, damage.size());
		
	}
	
	@Test
	public void MultipleInResolutionTriggeringExactLevel(){
		//Setup Test
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(3, resolution.size());
		assertEquals(4, damage.size());
		assertEquals(0, level.size());
		assertEquals(0, waitingRoom.size());
		
		//Perform Actions
		new ResolutionToDamage().execute(controller, controller);
		
		//Check Postconditions
		assertEquals(0, resolution.size());
		assertEquals(0, damage.size());
		assertEquals(1, level.size());
		assertEquals(6, waitingRoom.size());
	}
	
	@Test
	public void MultipleInResolutionTriggeringMoreThanLevel(){
		//Setup Test
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(3, resolution.size());
		assertEquals(6, damage.size());
		assertEquals(0, level.size());
		assertEquals(0, waitingRoom.size());
		
		//Perform Actions
		new ResolutionToDamage().execute(controller, controller);
		
		//Check Postconditions
		assertEquals(0, resolution.size());
		assertEquals(2, damage.size());
		assertEquals(1, level.size());
		assertEquals(6, waitingRoom.size());
	}
	
	@Test
	public void MultipleInResolutionTriggeringMultipleLevel(){
		//Setup Test
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		resolution.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		damage.add(character);
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(8, resolution.size());
		assertEquals(6, damage.size());
		assertEquals(0, level.size());
		assertEquals(0, waitingRoom.size());
		
		//Perform Actions
		new ResolutionToDamage().execute(controller, controller);
		
		//Check Postconditions
		assertEquals(0, resolution.size());
		assertEquals(0, damage.size());
		assertEquals(2, level.size());
		assertEquals(12, waitingRoom.size());
	}
	
	
}
