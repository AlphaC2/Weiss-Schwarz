package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.Character;
import model.card.Position;

public final class Slot {

	private SlotType slotType;
	private Character character;
	private List<Card> markers = new ArrayList<>();
	private Position position = null;

	public void stand() {
		position = Position.STANDING;
	}

	public void rest() {
		position = Position.RESTED;
	}

	public void reverse() {
		position = Position.REVERSED;
	}

	public Position getState() {
		return position;
	}

	@Override
	public String toString() {
		String m = "Slot [slotType=" + slotType;
		if (character != null) {
			m +=  ", Position=" + position + ", character=" + character +  ", markers=" + markers.size();
		}

		return m + "]";
	}

	public Slot(SlotType slotType) {
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
