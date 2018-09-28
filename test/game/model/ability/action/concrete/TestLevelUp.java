package game.model.ability.action.concrete;

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
import game.model.ability.action.concrete.LevelUp;
import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.Hand;
import game.model.board.LevelZone;
import game.model.board.Library;
import game.model.board.Stage;
import game.model.card.Card;
import game.model.card.Character;
import game.model.card.DummyFactory;
import game.model.card.DummyName;
import game.model.player.Player;

public class TestLevelUp {
	private Board board;
	private PlayerController controller1;
	private PlayerController controller2;
	private static int testNumber = 0;
	private String path = "CardData\\DummySet\\";
	private Character character;
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
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(dummyCard);
		}

		// Real Controller setup
		controller1 = new PlayerController("Real Player", mockReader, mockWriter);
		controller1.setDeck(deck);
		board = controller1.getBoard();
		
		controller2 = new PlayerController("Real Player2", mockReader, mockWriter);
		controller2.setDeck(deck);
		
		// Gamemanager setup
		new GameManager(controller1, controller2);
	}	

	//Setup Test
	//Check Preconditions
	//Perform Actions
	//Check Postconditions
	@Test
	public void NotEnoughDamageToLevelUp(){
		//Setup Test
		DamageZone damage = board.getDamageZone();
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());

		//Check Preconditions
		assertEquals(0, damage.size());
		
		//Perform Actions
		LevelUp levelUp = new LevelUp();
		levelUp.execute(controller1, controller2);
		
		//Check Postconditions
	}
	
	@Test
	public void LevelUpWithExactDamage(){
		//Setup Test
		LevelZone level = board.getLevel();
		DamageZone damage = board.getDamageZone();
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(character);
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(7, damage.size());
		assertEquals(0, level.size());
		
		//Perform Actions
		new LevelUp().execute(controller1, controller2);
		
		//Check Postconditions
		assertEquals(0, damage.size());
		assertEquals(1, level.size());
		assertEquals(6, board.getWaitingRoom().size());
		assertEquals(character, level.getCards().get(0));
	}
	
	@Test
	public void Level4(){
		//Setup Test
		LevelZone level = board.getLevel();
		level.add(dummyCard);
		level.add(dummyCard);
		level.add(dummyCard);
		DamageZone damage = board.getDamageZone();
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(dummyCard);
		damage.add(character);
		doReturn(character).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(7, damage.size());
		assertEquals(3, level.size());
		
		//Perform Actions
		new LevelUp().execute(controller1, controller2);
		
		//Check Postconditions
		assertEquals(0, damage.size());
		assertEquals(4, level.size());
		assertEquals(6, board.getWaitingRoom().size());
		assertEquals(character, level.getCards().get(3));
	}
}
