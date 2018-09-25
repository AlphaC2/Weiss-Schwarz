package game.model.board;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import game.model.card.Character;
import game.model.card.Position;

public class Stage implements Cloneable{
	private List<Slot> slots = new ArrayList<>();

	Stage() {
		super();
		for (SlotType slotType : SlotType.values()) {
			slots.add(new Slot(slotType));
		}
	}
	/* Front_Left	Front_Center	Front_Right
	 * 		Rear_Left		Rear_Right
	 * */
	
	public Slot getSlot(Character c){
		for (Slot slot : slots) {
			if (slot.getCharacter()!=null && slot.getCharacter().equals(c)){
				return slot;
			}
		}
		System.out.println("Character not on Stage");
		return null;
	}
	
	@Override
	public Stage clone(){
		Stage newStage = new Stage();
		newStage.slots = slots;
		return newStage;
	}
	
	public Slot getSlot(SlotType slotType){
		for (Slot slot : slots) {
			if (slot.getSlotType() == slotType){
				return slot;
			}
		}
		return null;
	}
	

	public List<Slot> getSlots() {
		return slots;
	}
	
	@JsonIgnore
	public List<Character> getCharacters() {
		List<Character> result = new ArrayList<Character>();
		for (Slot slot : slots) {
			Character c = slot.getCharacter() ;
			if (c != null)
				result.add(c);
		}
		return result;
	}
	
	public List<Slot> getCharacterByPosition(Position position) {
		List<Slot> result = new ArrayList<Slot>();
		for (Slot slot : slots) {
			Character c = slot.getCharacter() ;
			if (c != null && slot.getPosition() == position)
				result.add(slot);
		}
		
		return result;
	}
	
	@JsonIgnore
	public List<Character> getAttacking(){
		List<Character> result = new ArrayList<Character>();
		for (Slot slot : slots) {
			Character c = slot.getCharacter() ;
			if (c != null && SlotType.isFront(slot.getSlotType()) && slot.getPosition() == Position.STANDING){
				result.add(c);
			}
				
		}
		return result;
	}
	
	int cardsOnStage(){
		int total = 0;
		for (Slot slot : slots) {
			if (slot.getCharacter() != null){
				total++;
			}
		}
		return total;
	}

	public void place(Character c, SlotType slotType){
		c.flipFaceUp();
		getSlot(slotType).stand();
		for (Slot slot : slots) {
			if (slot.getSlotType() == slotType){
				slot.setCharacter(c);
			}
		}
	}
	

	void standAll() {
		for (Slot slot : slots) {
			Character c = slot.getCharacter();
			if (c != null){
				slot.stand();
			}
		}
	}
	
	public Character removeCharacter(SlotType s){
		Character c = null;
		for (Slot slot : slots) {
			if (slot.getSlotType() == s){
				c = slot.getCharacter();
				slot.setCharacter(null);
			}
		}
		return c;
		
	}

}
