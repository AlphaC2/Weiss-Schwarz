package command;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.*;
import org.mockito.*;

import app.ConsoleController;
import controller.GameManager;
import controller.PlayerController;
import io.ConsoleReadUserInput;
import model.card.Card;

public class TestDraw {
	
	private static PlayerController p1;
	private static PlayerController p2;
	@Mock
	private static ConsoleReadUserInput reader;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		p1 = new ConsoleController("P1");
		p2 = new ConsoleController("P2");
		when(reader.getChoice(anyString(), anyListOf(String.class))).thenReturn("AngelBeats1");
		p1.setReader(reader);
		p2.setReader(reader);
		GameManager gm = GameManager.getInstance();
		gm.init(p1, p2);
		p1.readDeck();
		p2.readDeck();
	}
	
	@Test
	public void draw1CardToHand(){
		int before = p1.getBoard().getHand().getCards().size();
		new Draw().execute(p1, null);
		assertEquals(before+1, p1.getBoard().getHand().getCards().size());
	}
	
	@Test
	public void draw1CardFromLibrary(){
		int sizeBefore = p1.getBoard().getLibrary().size();
		new Draw().execute(p1, null);
		assertEquals(sizeBefore-1, p1.getBoard().getLibrary().size());
	}
}
