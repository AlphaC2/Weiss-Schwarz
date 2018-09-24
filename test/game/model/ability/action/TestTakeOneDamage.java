package game.model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.PlayerController;
import game.io.Reader;
import game.model.ability.action.TakeOneDamage;
import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.ResolutionZone;
import game.model.card.Card;
import game.model.card.Character;
import game.model.player.Player;

public class TestTakeOneDamage {
	private Board board;
	
	@Mock
	Card mockCard;
	
	@Mock
	Character mockCharacter;
	
	@Mock
	PlayerController mockPlayerController;
	
	@Mock
	Reader mockReader;
	
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
		board.getHand().add(mockCharacter);
		when(mockPlayerController.getBoard()).thenReturn(board);
		when(mockPlayerController.getPlayer()).thenReturn(mockPlayer);
		mockPlayerController.setReader(mockReader);
		doReturn("mockPlayer").when(mockPlayer).getName();
		when(mockPlayerController.isAlive()).thenReturn(true);
	}

	//Setup Test
	//Check Preconditions
	//Perform Actions
	//Check Postconditions
	@Test
	public void OneInResolutionNoDamage(){
		//Setup Test
		ResolutionZone resolution = board.getResolutionZone();
		resolution.add(mockCharacter);
		
		//Check Preconditions
		assertEquals(0, board.getDamageZone().size());
		assertEquals(1, resolution.size());
		
		//Perform Actions
		new TakeOneDamage().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(1, board.getDamageZone().size());
		assertEquals(0, resolution.size());
		assertEquals(mockCharacter, board.getDamageZone().getCards().get(0));
	}
	
	@Test
	public void OneInResolutionSixInDamage(){
		//Setup Test
		ResolutionZone resolution = board.getResolutionZone();
		resolution.add(mockCharacter);
		DamageZone damage = board.getDamageZone();
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(6, board.getDamageZone().size());
		assertEquals(1, resolution.size());

		//Perform Actions
		new TakeOneDamage().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(0, board.getDamageZone().size());
		assertEquals(0, resolution.size());
		assertEquals(1, board.getLevel().size());
		assertEquals(6, board.getWaitingRoom().size());
		assertEquals(mockCharacter, board.getLevel().getCards().get(0));
	}
	
	@Test
	public void FourInResolution(){
		//Setup Test
		ResolutionZone resolution = board.getResolutionZone();
		resolution.add(mockCharacter);
		resolution.add(mockCharacter);
		resolution.add(mockCharacter);
		resolution.add(mockCharacter);
		
		//Check Preconditions
		assertEquals(4, resolution.size());
		
		//Perform Actions
		new TakeOneDamage().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(1, board.getDamageZone().size());
		assertEquals(3, resolution.size());
	}
	
	
}
