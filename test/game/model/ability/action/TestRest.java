package game.model.ability.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import game.controller.GameManager;
import game.controller.PlayerController;
import game.io.Reader;
import game.io.Writer;
import game.model.ability.action.Rest;
import game.model.ability.action.condition.Condition;
import game.model.ability.action.condition.HasTrait;
import game.model.board.Board;
import game.model.board.Slot;
import game.model.board.SlotType;
import game.model.card.Card;
import game.model.card.Character;
import game.model.card.Position;
import game.util.Util;

import org.junit.runners.Parameterized;
@SuppressWarnings("rawtypes")
@RunWith(Parameterized.class)
public class TestRest {
	private Board board;
	private SlotType slotParam;
	private Position positionParam;
	private List<Condition> conditionParam;
	private List<String> traitParams;
	private PlayerController controller1;
	private PlayerController controller2;
	
	@Mock
	Card mockCard;

	@Mock
	Character mockCharacter;

	@Mock
	Reader mockReader;

	@Mock
	Writer mockWriter;

	@Parameterized.Parameters(name = "{index} : parameters({0})")
	public static Collection<Object[]> parameters() {
		Collection<Object[]> result = new ArrayList<>();
		List<Position> positions = Arrays.asList(Position.values());
		List<SlotType> slotTypes = Arrays.asList(SlotType.values());
		String trait = "MUSIC";
		String[] traits = { trait, trait };
		List<List<String>> traitList = Util.powerSet(traits);
		
		Condition[] conditions = new Condition[] { new HasTrait(trait) };

		List<List<Condition>> conditionPermutations = Util.powerSet(conditions);

		positions.forEach(p -> slotTypes.forEach(s -> conditionPermutations
				.forEach(c -> traitList.forEach(item -> result.add(new Object[] {s, p, c, item} )))));

		return result;
	}
	
	public TestRest(SlotType s, Position p, List<Condition> c, List<String> trait){
		this.slotParam = s;
		this.positionParam = p;
		this.conditionParam = c;
		traitParams = trait;
	}
	

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		List<Card> deck = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			deck.add(mockCard);
		}
		board = new Board(deck);

		// Real Controller setup
		controller1 = new PlayerController("Real Player", mockReader, mockWriter);
		controller1.setDeck(deck);
		board = controller1.getBoard();
		
		controller2 = new PlayerController("Real Player2", mockReader, mockWriter);
		controller2.setDeck(deck);
		
		// Gamemanager setup
		new GameManager(controller1, controller2);
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSlotPosition() {
		boolean flag;
		Slot s = board.getStage().getSlot(slotParam);
		Position expected;
		when(mockCharacter.getTraits()).thenReturn(traitParams);
		when(mockCharacter.toShortString()).thenReturn("MOCK");
		doReturn(s).when(mockReader).getChoice(anyString(), anyList());

		s.setCharacter(mockCharacter);
		s.setPosition(positionParam);
		assertEquals(positionParam, s.getPosition());

		Rest rest = new Rest();
		for (Condition condition : conditionParam) {
			rest.addCondition(condition);
		}
		rest.setValidTargets(controller1, controller2);
		flag = rest.canActivate();

		if (positionParam == Position.STANDING && flag) {
			expected = Position.RESTED;
		} else {
			expected = positionParam;
		}

		try {
			rest.execute(controller1, controller2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!flag) {
//			verify(controller1).log(rest.failureMessage());
		}
		assertEquals(5, board.getStage().getSlots().size());
		assertEquals(mockCharacter, board.getStage().getSlot(slotParam).getCharacter());
		assertEquals(expected, board.getStage().getSlot(slotParam).getPosition());
	}

}
