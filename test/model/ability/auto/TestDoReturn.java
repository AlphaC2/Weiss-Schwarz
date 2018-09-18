package model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.ConsoleController;
import controller.PlayerController;
import controller.ReadUserInput;
import model.card.Card;

public class TestDoReturn {
	PlayerController Controller;
	
	@Mock
	ReadUserInput mockReader;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);

		doReturn(true,false).when(mockReader).getChoice(anyString());
		Controller = new ConsoleController("P1");
		Controller.setReader(mockReader);
	}
	
	@Test
	public void test(){
		assertTrue(Controller.getChoice(""));
		assertFalse(Controller.getChoice(""));
	}
}
