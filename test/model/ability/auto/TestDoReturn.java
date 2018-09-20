package model.ability.auto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.PlayerController;
import io.Reader;
import io.Writer;

public class TestDoReturn {
	PlayerController Controller;
	
	@Mock
	Reader mockReader;
	
	@Mock
	Writer mockWriter;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);

		doReturn(true,false).when(mockReader).getChoice(anyString());
		Controller = new PlayerController("P1", mockReader, mockWriter);
	}
	
	@Test
	public void test(){
		assertTrue(Controller.getChoice(""));
		assertFalse(Controller.getChoice(""));
	}
}
