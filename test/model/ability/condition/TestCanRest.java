package model.ability.condition;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.ability.condition.character.CharacterCondition;
import model.ability.condition.character.HasTrait;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Card;
import model.card.Character;
import model.card.Position;

public class TestCanRest {
	@Mock
	private Character mockCharacter;
	
	@Mock
	private Card mockCard;
	
	private Board board;
	private List<Card> deck;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);
	}
	
	@Test
	public void noCharOnStage(){
		CanRest condition = new CanRest();
		assertFalse(condition.check(board, null));
	}
	
	@Test
	public void restedCharOnFrontStage(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		s.setCharacter(mockCharacter);
		s.rest();
		CanRest condition = new CanRest();
		assertEquals(Position.RESTED, s.getPosition());
		assertFalse(condition.check(board, null));
	}
	
	@Test
	public void restedCharOnBackStage(){
		Slot s = board.getStage().getSlot(SlotType.REAR_LEFT);
		s.setCharacter(mockCharacter);
		s.rest();
		CanRest condition = new CanRest();
		assertEquals(Position.RESTED, s.getPosition());
		assertFalse(condition.check(board, null));
	}

	@Test
	public void reversedCharOnFrontStage(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		s.setCharacter(mockCharacter);
		s.reverse();
		CanRest condition = new CanRest();
		assertEquals(Position.REVERSED, s.getPosition());
		assertFalse(condition.check(board, null));
	}

	@Test
	public void reversedCharOnBackStage(){
		Slot s = board.getStage().getSlot(SlotType.REAR_LEFT);
		s.setCharacter(mockCharacter);
		s.reverse();
		CanRest condition = new CanRest();
		assertEquals(Position.REVERSED, s.getPosition());
		assertFalse(condition.check(board, null));
	}

	@Test
	public void standingCharOnFrontStage(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		s.setCharacter(mockCharacter);
		CanRest condition = new CanRest();
		assertEquals(Position.STANDING, s.getPosition());
		assertTrue(condition.check(board, null));
	}
	
	@Test
	public void standingCharOnBackStage(){
		Slot s = board.getStage().getSlot(SlotType.REAR_LEFT);
		s.setCharacter(mockCharacter);
		CanRest condition = new CanRest();
		assertEquals(Position.STANDING, s.getPosition());
		assertTrue(condition.check(board, null));
	}
	
	@Test
	public void characterWithoutMatchingTrait(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		s.setCharacter(mockCharacter);
		when(mockCharacter.getTrait1()).thenReturn("NOTMUSIC");
		when(mockCharacter.getTrait2()).thenReturn(null);
		HasTrait traitRequirement = new HasTrait("MUSIC");
		CanRest condition = new CanRest();
		
		condition.addCharCondition(traitRequirement);
		assertEquals(Position.STANDING, s.getPosition());
		assertFalse(condition.check(board, null));
	}
	
	@Test
	public void characterWithMatchingTrait(){
		Slot s = board.getStage().getSlot(SlotType.FRONT_CENTER);
		s.setCharacter(mockCharacter);
		when(mockCharacter.getTrait1()).thenReturn("MUSIC");
		when(mockCharacter.getTrait2()).thenReturn(null);
		CanRest condition = new CanRest();
		HasTrait traitRequirement = new HasTrait("MUSIC");
		condition.addCharCondition(traitRequirement);
		
		assertEquals(Position.STANDING, s.getPosition());
		assertTrue(condition.check(board, null));
	}
	
}
