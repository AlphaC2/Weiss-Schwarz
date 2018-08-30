package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Character;
import model.card.Position;

public class Stage {
	private List<Slot> stage = new ArrayList<>();

	Stage() {
		super();
		for (SlotType slotType : SlotType.values()) {
			stage.add(new Slot(slotType));
		}
	}
	/* Front_Left	Front_Center	Front_Right
	 * 		Rear_Left		Rear_Right
	 * */
	
	Character getCharacter(SlotType slotType){
		for (Slot slot : stage) {
			if (slot.getSlotType() == slotType){
				return slot.getCharacter();
			}
		}
		return null;
	}
	
	void place(Character c, SlotType slotType){
		c.flipFaceUp();
		c.stand();
		for (Slot slot : stage) {
			if (slot.getSlotType() == slotType){
				slot.setCharacter(c);
			}
		}
	}
	

	void standAll() {
		for (Slot slot : stage) {
			Character c = slot.getCharacter();
			if (c != null){
				c.stand();
			}
		}
	}
	
	Character removeCharacter(SlotType s){
		Character c = null;
		for (Slot slot : stage) {
			if (slot.getSlotType() == s){
				c = slot.getCharacter();
				slot.setCharacter(null);
			}
		}
		return c;
		
	}
	
	int cardsOnStage(){
		int total = 0;
		for (Slot slot : stage) {
			if (slot.getCharacter() != null){
				total++;
			}
		}
		return total;
	}
	
	boolean hasCharacter(SlotType s){
		for (Slot slot : stage) {
			if (slot.getSlotType() == s && slot.getCharacter() != null){
				return true;
			}
		}
		return false;
	}
	
	boolean rest(SlotType s){
		Character c = getCharacter(s);
		if (c == null){
			System.out.println("No Character in slot");
			return false;
		}else if(c.getState() != Position.STANDING){
			System.out.println("Character not Standing:Unable to attack");
			return false;
		}else{
			c.rest();
			return true;
		}
	}
	
	public Slot getSlot(Character c){
		for (Slot slot : stage) {
			if (slot.getCharacter().equals(c)){
				return slot;
			}
		}
		System.out.println("Character not on Stage");
		return null;
	}

	public List<Character> getCharacterByPosition(Position position) {
		List<Character> result = new ArrayList<Character>();
		for (Slot slot : stage) {
			Character c = slot.getCharacter() ;
			if (c != null && c.getState() == position)
				result.add(c);
		}
		
		return result;
	}
	
	public List<Character> getCharacters() {
		List<Character> result = new ArrayList<Character>();
		for (Slot slot : stage) {
			Character c = slot.getCharacter() ;
			if (c != null)
				result.add(c);
		}
		return result;
	}
	
	public List<Character> getAttacking(){
		List<Character> result = new ArrayList<Character>();
		for (Slot slot : stage) {
			Character c = slot.getCharacter() ;
			if (c != null && SlotType.isFront(slot.getSlotType()))
				result.add(c);
		}
		return result;
	}

	public List<Slot> getSlots() {
		return stage;
	}
	
}
