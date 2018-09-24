package game.model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.PlayerController;
import game.io.Reader;
import game.model.ability.action.LevelUp;
import game.model.board.Board;
import game.model.board.DamageZone;
import game.model.board.LevelZone;
import game.model.card.Card;
import game.model.card.Character;
import game.model.player.Player;

public class TestLevelUp {
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
	public void NotEnoughDamageToLevelUp(){
		//Setup Test
		DamageZone damage = board.getDamageZone();
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());

		//Check Preconditions
		assertEquals(0, damage.size());
		
		//Perform Actions
		LevelUp levelUp = new LevelUp();
		levelUp.execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		verify(mockPlayerController).log(levelUp.failureMessage());
	}
	
	@Test
	public void LevelUpWithExactDamage(){
		//Setup Test
		LevelZone level = board.getLevel();
		DamageZone damage = board.getDamageZone();
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCharacter);
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(7, damage.size());
		assertEquals(0, level.size());
		
		//Perform Actions
		new LevelUp().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(0, damage.size());
		assertEquals(1, level.size());
		assertEquals(6, board.getWaitingRoom().size());
		assertEquals(mockCharacter, level.getCards().get(0));
	}
	
	@Test
	public void Level4(){
		//Setup Test
		LevelZone level = board.getLevel();
		level.add(mockCard);
		level.add(mockCard);
		level.add(mockCard);
		DamageZone damage = board.getDamageZone();
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCard);
		damage.add(mockCharacter);
		doReturn(mockCharacter).when(mockReader).getChoice(anyString(), anyList());
		
		//Check Preconditions
		assertEquals(7, damage.size());
		assertEquals(3, level.size());
		
		//Perform Actions
		new LevelUp().execute(mockPlayerController, mockPlayerController);
		
		//Check Postconditions
		assertEquals(0, damage.size());
		assertEquals(4, level.size());
		assertEquals(6, board.getWaitingRoom().size());
		assertEquals(mockCharacter, level.getCards().get(3));
		verify(mockPlayerController).level4();
	}
}
