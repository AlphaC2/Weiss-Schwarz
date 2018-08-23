package test.board;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.board.MemoryZone;
import model.card.Card;
import model.exceptions.CardNotInZoneException;

public class TestMemory {
	private MemoryZone memory;
	
	@Mock
	private Card mockCard;

	@Mock
	private List<Card> mockMemory;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		mockMemory.add(mockCard);
		memory = new MemoryZone();
	}
	
	@After
	public void tearDown(){
		memory = null;
	}
	
	@Test
	public void memoryEmptyOnCreation(){
		assertEquals(memory.size(), 0);
	}
	
	@Test
	public void addOneToMemory(){
		memory.sendToMemory(mockCard);
		assertEquals(memory.size(), 1);
	}
	
	@Test
	public void removeOneFromMemory() throws CardNotInZoneException{
		memory.sendToMemory(mockCard);
		memory.retrieveFromMemory(mockCard);
		assertEquals(memory.size(), 0);
	}
	
	@Test(expected=CardNotInZoneException.class)
	public void removeEmptyMemory() throws CardNotInZoneException{
		memory.retrieveFromMemory(mockCard);
	}
	
	
}
