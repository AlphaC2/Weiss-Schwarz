package model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.PlayerController;
import controller.ReadUserInput;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Card;
import model.card.Character;
import model.card.Colour;

public class TestPlayCard {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	Character mockCharacter;
	
	@Mock
	Character mockCharacter2;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Mock
	ReadUserInput mockReader;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);
		board.getHand().add(mockCharacter);
		when(mockPlayerController.getBoard()).thenReturn(board);
		mockPlayerController.setReader(mockReader);
	}

	//Setup Test
	//Check Preconditions
	//Perform Actions
	//Check Postconditions

	@Test
	public void CharacterAbovePlayerLevel(){
		//Setup Test
		when(mockCharacter.getLevel()).thenReturn(3);
		board.getLevel().add(mockCharacter2);

		//Check Preconditions
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );

		//Perform Actions
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);

		//Check Postconditions
		verify(mockPlayerController).log(playCard.failureMessage());
	}

	@Test
	public void CharacterSameLevel(){
		//Setup Test
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		when(mockCharacter.getLevel()).thenReturn(1);
		doReturn(mockCharacter,slot).when(mockReader).getChoice(anyString(), anyList());
		board.getLevel().add(mockCard);

		//Check Preconditions
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());	
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );

		//Perform Actions
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);

		//Check Postconditions
		assertEquals(mockCharacter, slot.getCharacter());
		assertEquals(0, board.getHand().size());
	}

	@Test
	public void CharacterDoesNotMeetColourRequirement(){
		//Setup Test
		when(mockCharacter.getLevel()).thenReturn(1);
		board.getLevel().add(mockCharacter2);
		when(mockCharacter.getColour()).thenReturn(Colour.YELLOW);
		when(mockCharacter2.getColour()).thenReturn(Colour.RED);
		
		//Check Preconditions
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());	
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );
		assertNotEquals(mockCharacter.getColour(), mockCharacter2.getColour());
		
		//Perform Actions
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		verify(mockPlayerController).log(playCard.failureMessage());
	}
	
	@Test
	public void PlayCharacterWithNotEnoughCost(){
		//Setup Test
		when(mockCharacter.getCost()).thenReturn(1);
		
		//Verify preconditions
		assertEquals(0, board.getStock().size());
		assertEquals(1, board.getHand().size());
		
		//Perform actions
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);
		
		//Verify postconditions
		verify(mockPlayerController).log(playCard.failureMessage());
	}

	@Test
	public void PlayCharacterWithExactlyEnoughCost(){
		//Setup Test
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		when(mockCharacter.getCost()).thenReturn(1);
		doReturn(mockCharacter,slot).when(mockReader).getChoice(anyString(), anyList());
		board.getStock().add(mockCard);
		
		//Verify preconditions
		assertEquals(1, board.getStock().size());
		assertEquals(1, board.getHand().size());
		
		//Perform actions
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);
		
		//Verify postconditions
		assertEquals(0, board.getStock().size());
		assertEquals(0, board.getHand().size());
		assertEquals(1, board.getWaitingRoom().size());
		assertEquals(mockCharacter, slot.getCharacter());
	}
	
	@Test
	public void CharacterMeetsLevelAndColourRequirements(){
		//Setup Test
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		when(mockCharacter.getLevel()).thenReturn(2);
		doReturn(mockCharacter,slot).when(mockReader).getChoice(anyString(), anyList());
		board.getLevel().add(mockCard);
		board.getLevel().add(mockCharacter2);
		when(mockCharacter.getColour()).thenReturn(Colour.YELLOW);
		when(mockCharacter2.getColour()).thenReturn(Colour.YELLOW);
		
		//Check Preconditions
		assertEquals(2, board.getLevel().size());
		assertEquals(2, mockCharacter.getLevel());
		assertEquals(1, board.getHand().size());
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );
		assertEquals(mockCharacter.getColour(), mockCharacter2.getColour());
		
		//Perform Actions
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(mockCharacter, slot.getCharacter());
		assertEquals(0, board.getHand().size());
		assertEquals(2, board.getLevel().size());
	}

	@Test
	public void PlayCharacterOnTopOfCharacter(){
		//Setup Test
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		slot.setCharacter(mockCharacter2);
		doReturn(mockCharacter, slot).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(1, board.getHand().size());
		assertEquals(0, board.getWaitingRoom().size());
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );
		assertEquals(mockCharacter2, slot.getCharacter());
		
		//Perform Actions
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);

		//Check Postconditions		
		assertEquals(mockCharacter, slot.getCharacter());
		assertEquals(0, board.getHand().size());
		assertEquals(1, board.getWaitingRoom().size());
		assertEquals(mockCharacter2, board.getWaitingRoom().getCards().get(0));
	}
	
}
