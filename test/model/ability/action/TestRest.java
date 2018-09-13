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
import controller.ReadUserInput;
import model.ability.condition.Condition;
import model.ability.condition.HasTrait;
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
	static Collection<params> result = new ArrayList<>();
	private params sp;
	List<Condition> conditions;

	public static class params {
		public SlotType s;
		public Position p;
		public List<Condition> c;
		public String trait1;
		public String trait2;

		params(SlotType s, Position p, List<Condition> c, List<String> trait) {
			this.s = s;
			this.p = p;
			this.c = c;
			if (trait.size() >= 1){
				trait1 = trait.get(0);
			}
			if (trait.size() == 2){
				trait2 = trait.get(1);
			}
		}
		
		public String toString(){
			return "Params [slot=" + s + ", position=" + p + ", trait1=" + trait1 + ", trait2=" + trait2 + 
					", condition=" + Arrays.toString(c.toArray());
		}
	}

	@Mock
	Card mockCard;

	@Mock
	Character mockCharacter;

	@Mock
	PlayerController mockPlayerController;

	@Mock
	ReadUserInput mockReader;

	@Parameterized.Parameters(name = "{index} : parameters({0})")
	public static Collection<params> parameters() {
		List<Position> positions = Arrays.asList(Position.values());
		List<SlotType> slotTypes = Arrays.asList(SlotType.values());
		String trait = "MUSIC";
		String[] traits = { trait, trait };
		List<List<String>> traitList = Util.powerSet(traits);
		
		Condition[] conditions = new Condition[] { new HasTrait(trait) };

		List<List<Condition>> conditionPermutations = Util.powerSet(conditions);

		positions.forEach(p -> slotTypes.forEach(s -> conditionPermutations
				.forEach(c -> traitList.forEach(item -> result.add(new params(s, p, c, item))))));

		return result;
	}

	public TestRest(params sp) {
		this.sp = sp;
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

	@Test
	public void testSlotPosition() {
		boolean flag;
		Slot s = board.getStage().getSlot(sp.s);
		Position expected;

		when(mockCharacter.getTrait1()).thenReturn(sp.trait1);
		when(mockCharacter.getTrait2()).thenReturn(sp.trait2);
		when(mockCharacter.toShortString()).thenReturn("MOCK");
		when(mockPlayerController.getChoice(anyString(), anyList())).thenReturn(s);

		s.setCharacter(mockCharacter);
		s.setPosition(sp.p);
		assertEquals(sp.p, s.getPosition());

		Rest rest = new Rest();
		for (Condition condition : sp.c) {
			rest.addCondition(condition);
		}
		rest.setValidTargets(mockPlayerController, mockPlayerController);
		flag = rest.canActivate();

		if (sp.p == Position.STANDING && flag) {
			expected = Position.RESTED;
		} else {
			expected = sp.p;
		}

		try {
			rest.execute(mockPlayerController, mockPlayerController);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!flag) {
			verify(mockPlayerController).log(rest.failureMessage());
		}
		assertEquals(mockCharacter, s.getCharacter());
		assertEquals(expected, s.getPosition());
	}

}
