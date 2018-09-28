package game.util;

import org.junit.Test;

import game.model.board.Slot;
import game.model.board.SlotType;
import game.model.card.Position;

public class TestPassingLater {
	private Slot s = new Slot(SlotType.REAR_LEFT);
	
	
	@Test
	public void method(){
		slotHolder sh = new slotHolder(s);
		s.setPosition(Position.REVERSED);
		System.out.println(sh.getSlot().getPosition());
	}
	
	private class slotHolder{
		private Slot s;
		public slotHolder(Slot s){
			this.s = s;
		}
		public Slot getSlot(){
			return s;
		}
	}
	
}
