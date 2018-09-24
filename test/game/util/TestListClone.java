package game.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import game.model.board.Slot;
import game.model.board.SlotType;

public class TestListClone {

	@Test
	public void test1(){
		Slot s = new Slot(SlotType.FRONT_CENTER);
		List<Slot> slots = new ArrayList<>();
		slots.add(s);
		List<Slot> slots2 = new ArrayList<>(slots);
		
		s.rest();
		
		System.out.println(slots.get(0).getPosition());
		System.out.println(slots2.get(0).getPosition());
		
		
	}
}
