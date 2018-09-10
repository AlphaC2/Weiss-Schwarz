package model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
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
	
	@Test
	public void testCharacterAboveLevel(){
		when(mockCharacter.getLevel()).thenReturn(3);
		board.getLevel().add(mockCard);
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());		
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );
		
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);
		verify(mockPlayerController).log(playCard.failureMessage());
	}
	
	@Test
	public void testCharacterSameLevel(){
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		when(mockCharacter.getLevel()).thenReturn(1);
		doReturn(mockCharacter,slot).when(mockReader).getChoice(anyString(), anyList());
		board.getLevel().add(mockCard);
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());	
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);
		assertEquals(mockCharacter, slot.getCharacter());
		assertEquals(0, board.getHand().size());
	}
	
	@Test
	public void testPlayOnTopOfCharacter(){
		Slot slot = board.getStage().getSlot(SlotType.FRONT_CENTER);
		slot.setCharacter(mockCharacter2);
		when(mockCharacter.getLevel()).thenReturn(1);
		doReturn(mockCharacter,slot).when(mockReader).getChoice(anyString(), anyList());
		
		board.getLevel().add(mockCard);
		assertEquals(1, board.getLevel().size());
		assertEquals(1, board.getHand().size());
		assertEquals(0, board.getWaitingRoom().size());
		assertEquals(mockCharacter, board.getHand().getCards().get(0) );
		assertEquals(mockCharacter2, slot.getCharacter());
		
		PlayCard playCard = new PlayCard();
		playCard.execute(mockPlayerController, mockPlayerController);
		
		assertEquals(mockCharacter, slot.getCharacter());
		assertEquals(0, board.getHand().size());
		assertEquals(1, board.getWaitingRoom().size());
		assertEquals(mockCharacter2, board.getWaitingRoom().getCards().get(0));
	}
}
