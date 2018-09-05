package command;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import org.junit.*;
import org.mockito.*;

import app.ConsoleController;
import controller.GameManager;
import controller.PlayerController;
import io.ConsoleReadUserInput;

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
	
	@Test
	public void draw1CardNotFromDamageZone(){
		int sizeBefore = p1.getBoard().getDamageZone().size();
		new Draw().execute(p1, null);
		assertEquals(sizeBefore, p1.getBoard().getDamageZone().size());
	}
	
	@Test
	public void draw1CardNotFromLevelZone(){
		int sizeBefore = p1.getBoard().getLevel().size();
		new Draw().execute(p1, null);
		assertEquals(sizeBefore, p1.getBoard().getLevel().size());
	}
	
	@Test
	public void draw1CardNotFromMemoryZone(){
		int sizeBefore = p1.getBoard().getMemoryZone().size();
		new Draw().execute(p1, null);
		assertEquals(sizeBefore, p1.getBoard().getMemoryZone().size());
	}
	
	@Test
	public void draw1CardNotFromStage(){
		int sizeBefore = p1.getBoard().getStage().getCharacters().size();
		new Draw().execute(p1, null);
		assertEquals(sizeBefore, p1.getBoard().getStage().getCharacters().size());
	}
	
	@Test
	public void draw1CardNotFromStock(){
		int sizeBefore = p1.getBoard().getStock().size();
		new Draw().execute(p1, null);
		assertEquals(sizeBefore, p1.getBoard().getStock().size());
	}
	
	@Test
	public void draw1CardNotFromWaitingRoom(){
		int sizeBefore = p1.getBoard().getWaitingRoom().size();
		new Draw().execute(p1, null);
		assertEquals(sizeBefore, p1.getBoard().getWaitingRoom().size());
	}
}
