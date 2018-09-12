package model.ability.action;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.PlayerController;
import model.board.Board;
import model.card.Card;

public class TestPayStock {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);
		when(mockPlayerController.getBoard()).thenReturn(board);
		when(mockPlayerController.isAlive()).thenReturn(true);
	}
	
	@Test
	public void NoStock(){
		assertEquals(0, board.getStock().size());
		assertEquals(0, board.getWaitingRoom().size());
		PayStock payStock = new PayStock(1);
		payStock.execute(mockPlayerController, mockPlayerController);
		
		assertEquals(0, board.getStock().size());
		assertEquals(0, board.getWaitingRoom().size());
		verify(mockPlayerController).log(payStock.failureMessage());
	}
	
	@Test
	public void NotEnoughStock(){
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);

		assertEquals(3, board.getStock().size());
		assertEquals(0, board.getWaitingRoom().size());
		PayStock payStock = new PayStock(5);
		payStock.execute(mockPlayerController, mockPlayerController);
		
		assertEquals(3, board.getStock().size());
		assertEquals(0, board.getWaitingRoom().size());
		verify(mockPlayerController).log(payStock.failureMessage());
	}
	
	@Test
	public void ExactlyEnoughStock(){
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		
		assertEquals(3, board.getStock().size());
		assertEquals(0, board.getWaitingRoom().size());
		PayStock payStock = new PayStock(3);
		payStock.execute(mockPlayerController, mockPlayerController);
		
		assertEquals(0, board.getStock().size());
		assertEquals(3, board.getWaitingRoom().size());
	}
	
	
	@Test
	public void ExtraStock(){
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		
		assertEquals(4, board.getStock().size());
		assertEquals(0, board.getWaitingRoom().size());
		PayStock payStock = new PayStock(3);
		payStock.execute(mockPlayerController, mockPlayerController);
		
		assertEquals(1, board.getStock().size());
		assertEquals(3, board.getWaitingRoom().size());
	}
	
	@Test
	public void chainStock(){
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		board.getStock().add(mockCard);
		
		assertEquals(4, board.getStock().size());
		assertEquals(0, board.getWaitingRoom().size());
		PayStock payStock = new PayStock(2);
		PayStock payStock2 = new PayStock(2);
		payStock.setNextAction(payStock2);
		payStock.execute(mockPlayerController, mockPlayerController);
		assertEquals(0, board.getStock().size());
		assertEquals(4, board.getWaitingRoom().size());
	}
	
	
	
}
