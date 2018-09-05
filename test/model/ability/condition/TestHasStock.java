package model.ability.condition;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.board.Board;
import model.board.Stock;

public class TestHasStock {
	@Mock
	private Board mockBoard;
	
	@Mock
	private Stock mockStock;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		when(mockBoard.getStock()).thenReturn(mockStock);
	}
	
	@Test
	public void emptyStock(){
		when(mockStock.size()).thenReturn(0);
		boolean check = new HasStock(1).check(mockBoard, mockBoard);
		assertFalse(check);
	}
	
	@Test
	public void notEnoughStock(){
		when(mockStock.size()).thenReturn(3);
		boolean check = new HasStock(4).check(mockBoard, mockBoard);
		assertFalse(check);
	}
	
	@Test
	public void enoughStock(){
		when(mockStock.size()).thenReturn(5);
		boolean check = new HasStock(4).check(mockBoard, mockBoard);
		assertTrue(check);
	}
	
	@Test
	public void exactlyEnoughStock(){
		when(mockStock.size()).thenReturn(5);
		boolean check = new HasStock(5).check(mockBoard, mockBoard);
		assertTrue(check);
	}
	
}
