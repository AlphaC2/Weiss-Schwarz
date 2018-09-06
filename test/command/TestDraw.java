package command;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.mockito.*;

import controller.PlayerController;
import io.ConsoleReadUserInput;
import model.board.Board;
import model.card.Card;
import model.player.Player;

public class TestDraw {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	PlayerController p1;
	
	@Mock
	private static ConsoleReadUserInput reader;
	
	@Mock
	Player mockPlayer;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);
		when(p1.getBoard()).thenReturn(board);
		when(p1.getPlayer()).thenReturn(mockPlayer);
		when(mockPlayer.getName()).thenReturn("p1");
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
