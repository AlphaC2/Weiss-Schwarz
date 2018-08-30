package model.board;

import java.util.List;

import model.card.Card;
import model.card.Character;

public final class Slot {
	
	private SlotType slotType;
	private Character character;
	private List<Card> markers;


	@Override
	public String toString() {
		return "Slot [slotType=" + slotType + ", character=" + character +", markers=" + markers.size() +"]";
	}

	public Slot(SlotType slotType){
		this.slotType = slotType;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public List<Card> getMarkers() {
		return markers;
	}

	public void setMarkers(List<Card> markers) {
		this.markers = markers;
	}

	public SlotType getSlotType() {
		return slotType;
	}
	
	
	

}
