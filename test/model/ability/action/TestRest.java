package model.ability.action;

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
import org.junit.runners.Parameterized;

import controller.PlayerController;
import io.Reader;
import model.ability.action.condition.Condition;
import model.ability.action.condition.HasTrait;
import model.board.Board;
import model.board.Slot;
import model.board.SlotType;
import model.card.Card;
import model.card.Character;
import model.card.Position;
import util.Util;
@SuppressWarnings("rawtypes")
@RunWith(Parameterized.class)
public class TestRest {
	private Board board;
	private SlotType slotParam;
	private Position positionParam;
	private List<Condition> conditionParam;
	private List<String> traitParams;
	
	@Mock
	Card mockCard;

	@Mock
	Character mockCharacter;

	@Mock
	PlayerController mockPlayerController;

	@Mock
	Reader mockReader;

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
		when(mockPlayerController.getBoard()).thenReturn(board);
		mockPlayerController.setReader(mockReader);
		when(mockPlayerController.isAlive()).thenReturn(true);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSlotPosition() {
		boolean flag;
		Slot s = board.getStage().getSlot(slotParam);
		Position expected;
		when(mockCharacter.getTraits()).thenReturn(traitParams);
		when(mockCharacter.toShortString()).thenReturn("MOCK");
		when(mockPlayerController.getChoice(anyString(), anyList())).thenReturn(s);

		s.setCharacter(mockCharacter);
		s.setPosition(positionParam);
		assertEquals(positionParam, s.getPosition());

		Rest rest = new Rest();
		for (Condition condition : conditionParam) {
			rest.addCondition(condition);
		}
		rest.setValidTargets(mockPlayerController, mockPlayerController);
		flag = rest.canActivate();

		if (positionParam == Position.STANDING && flag) {
			expected = Position.RESTED;
		} else {
			expected = positionParam;
		}

		try {
			rest.execute(mockPlayerController, mockPlayerController);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!flag) {
			verify(mockPlayerController).log(rest.failureMessage());
		}
		assertEquals(5, board.getStage().getSlots().size());
		assertEquals(mockCharacter, board.getStage().getSlot(slotParam).getCharacter());
		assertEquals(expected, board.getStage().getSlot(slotParam).getPosition());
	}

}
