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
import game.model.ability.action.concrete.PlayCardFromHand;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.board.SlotType;
import game.model.card.Card;
import game.model.card.Character;

public class TestPlayCard {
	private Board board;
	private static int testNum = 0;
	private static String testName = "TestPlayCard";
	private String path = "CardData\\DummySet\\";
	private PlayerController controller1;
	private PlayerController controller2;
	
	@Mock
	Card mockCard;
	
	@Mock
	Reader mockReader;

	@Mock
	Writer mockWriter;
	
	@Before
	public void init(){
		testNum++;
		System.out.println(testName + " TestNumber " + testNum);
		MockitoAnnotations.initMocks(this);
		
		//Setup Cards
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);

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
	public void CharacterAbovePlayerLevel(){
		//Setup Test
		Character character = (Character) CardXMLReader.read(path + "LevelOneCharacter.xml");
		board.getHand().add(character);
		
		//Check Preconditions
		assertEquals(0, board.getLevel().size());
		assertEquals(1, board.getHand().size());
		assertEquals(character, board.getHand().getCards().get(0) );
		assertEquals(1, character.getLevel());
		
		//Perform Actions
		PlayCardFromHand playCard = new PlayCardFromHand();
		playCard.execute(controller1, controller2);

		//Check Postconditions
	}

	@Test
	public void CharacterSameLevelAndColour(){
		//Setup Test
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		Character character = (Character) CardXMLReader.read(path + "LevelOneCharacter.xml");
		board.getHand().add(character);
		
		Character yellowCharacter = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		board.getLevel().add(yellowCharacter);
		
		doReturn(character,slot).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());	
		assertEquals(character, board.getHand().getCards().get(0) );
		assertEquals(1, character.getLevel());

		//Perform Actions
		PlayCardFromHand playCard = new PlayCardFromHand();
		playCard.execute(controller1, controller2);

		//Check Postconditions
		assertEquals(character, slot.getCharacter());
		assertEquals(0, board.getHand().size());
	}

	@Test
	public void CharacterDoesNotMeetColourRequirement(){
		//Setup Test
		Character red = (Character) CardXMLReader.read(path + "RedCharacter.xml");
		board.getLevel().add(red);
		Character character = (Character) CardXMLReader.read(path + "LevelOneCharacter.xml");
		board.getHand().add(character);
		
		//Check Preconditions
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());	
		assertEquals(character, board.getHand().getCards().get(0) );
		assertNotEquals(character.getColour(), board.getLevel().getCards().get(0).getColour());
		
		//Perform Actions
		PlayCardFromHand playCard = new PlayCardFromHand();
		playCard.execute(controller1, controller2);
		
		//Check Postconditions
	}
	
	@Test
	public void PlayCharacterWithNotEnoughCost(){
		//Setup Test
		Character character = (Character) CardXMLReader.read(path + "CostOneCharacter.xml");
		board.getHand().add(character);
		//when(character.getCost()).thenReturn(1);
		
		
		//Verify preconditions
		assertEquals(0, board.getStock().size());
		assertEquals(1, board.getHand().size());
		
		//Perform actions
		PlayCardFromHand playCard = new PlayCardFromHand();
		playCard.execute(controller1, controller2);
		
		//Verify postconditions
	}

	@Test
	public void PlayCharacterWithExactlyEnoughCost(){
		//Setup Test
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		Character costOne = (Character) CardXMLReader.read(path + "CostOneCharacter.xml");
		board.getHand().add(costOne);
		board.getStock().add(mockCard);
		doReturn(costOne,slot).when(mockReader).getChoice(anyString(), anyList());
		
		//Verify preconditions
		assertEquals(1, board.getStock().size());
		assertEquals(1, board.getHand().size());
		
		//Perform actions
		PlayCardFromHand playCard = new PlayCardFromHand();
		playCard.execute(controller1, controller2);
		
		//Verify postconditions
		assertEquals(0, board.getStock().size());
		assertEquals(0, board.getHand().size());
		assertEquals(1, board.getWaitingRoom().size());
		assertEquals(costOne, slot.getCharacter());
	}
	
	@Test
	public void PlayCharacterOnTopOfCharacter(){
		//Setup Test
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		Character character = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		slot.setCharacter(character);
		
		Character character2 = (Character) CardXMLReader.read(path + "BasicCharacter.xml");
		board.getHand().add(character2);
		doReturn(character2, slot).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(1, board.getHand().size());
		assertEquals(0, board.getWaitingRoom().size());
		assertEquals(character2, board.getHand().getCards().get(0) );
		assertEquals(character, slot.getCharacter());
		
		//Perform Actions
		PlayCardFromHand playCard = new PlayCardFromHand();
		playCard.execute(controller1, controller2);

		//Check Postconditions		
		assertEquals(character2, slot.getCharacter());
		assertEquals(0, board.getHand().size());
		assertEquals(1, board.getWaitingRoom().size());
		assertEquals(character, board.getWaitingRoom().getCards().get(0));
	}
	
}
