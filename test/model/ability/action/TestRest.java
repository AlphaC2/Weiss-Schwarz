package model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyListOf;
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
import model.card.Position;

public class TestRest {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	Character mockCharacter;
	
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
		when(mockPlayerController.getBoard()).thenReturn(board);
		mockPlayerController.setReader(mockReader);
	}
	
	@Test
	public void emptyStage(){
		List<Slot> slots = board.getStage().getSlots();
		for (Slot slot : slots) {
			assertNull(slot.getCharacter());
		}
		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(rest.failureMessage());
	}
	
	@Test
	public void onlyRestedCharOnFrontRow(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);

		s.setCharacter(mockCharacter);
		s.rest();
		assertEquals(Position.RESTED, s.getPosition());
		
		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(rest.failureMessage());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	@Test
	public void onlyRestedCharOnBackRow(){
		Slot s = board.getStage().getSlot(SlotType.REAR_LEFT);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		s.rest();
		assertEquals(Position.RESTED, s.getPosition());
		
		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(rest.failureMessage());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	@Test
	public void onlyReversedCharOnFrontRow(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		s.reverse();
		assertEquals(Position.REVERSED, s.getPosition());
		
		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(rest.failureMessage());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	@Test
	public void onlyReversedCharOnBackRow(){
		Slot s = board.getStage().getSlot(SlotType.REAR_LEFT);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		s.reverse();
		assertEquals(Position.REVERSED, s.getPosition());
		
		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		
		verify(mockPlayerController).log(rest.failureMessage());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	@Test
	public void standingCharOnFrontRow(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		assertEquals(Position.STANDING, s.getPosition());
		
		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	@Test
	public void standingCharOnBackRow(){
		Slot s = board.getStage().getSlot(SlotType.REAR_LEFT);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		assertEquals(Position.STANDING, s.getPosition());

		Rest rest = new Rest();
		rest.execute(mockPlayerController, mockPlayerController);
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	@Test
	public void standingFrontRowNotMeetingTraitRequirement(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		assertEquals(Position.STANDING, s.getPosition());
		
		Rest rest = new Rest("MUSIC");
		rest.execute(mockPlayerController, mockPlayerController);
		assertEquals(Position.STANDING, s.getPosition());
		assertEquals(mockCharacter, s.getCharacter());
		verify(mockPlayerController).log(rest.failureMessage());
	}
	
	@Test
	public void restedFrontRowMeetingTrait(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn("MUSIC");
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		s.rest();
		assertEquals(Position.RESTED, s.getPosition());
		
		Rest rest = new Rest("MUSIC");
		rest.execute(mockPlayerController, mockPlayerController);
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(mockCharacter, s.getCharacter());
		verify(mockPlayerController).log(rest.failureMessage());
	}
	
	@Test
	public void reversedFrontRowMeetingTrait(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn("MUSIC");
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		s.reverse();
		assertEquals(Position.REVERSED, s.getPosition());
		
		Rest rest = new Rest("MUSIC");
		rest.execute(mockPlayerController, mockPlayerController);
		assertEquals(Position.REVERSED, s.getPosition());
		assertEquals(mockCharacter, s.getCharacter());
		verify(mockPlayerController).log(rest.failureMessage());
	}
	
	@Test
	public void standingFrontRowMeetingTrait1Requirement(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn("MUSIC");
		when(mockCharacter.getTrait2()).thenReturn(null);
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		assertEquals(Position.STANDING, s.getPosition());
		
		Rest rest = new Rest("MUSIC");
		rest.execute(mockPlayerController, mockPlayerController);
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	@Test
	public void standingFrontRowMeetingTrait2Requirement(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		
		when(mockCharacter.getTrait1()).thenReturn(null);
		when(mockCharacter.getTrait2()).thenReturn("MUSIC");
		when(mockPlayerController.getChoice(anyString(), anyListOf(Slot.class))).thenReturn(s);
		
		s.setCharacter(mockCharacter);
		assertEquals(Position.STANDING, s.getPosition());
		
		Rest rest = new Rest("MUSIC");
		rest.execute(mockPlayerController, mockPlayerController);
		assertEquals(Position.RESTED, s.getPosition());
		assertEquals(mockCharacter, s.getCharacter());
	}
	
	
}
