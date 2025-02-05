package game.model.board;

import java.util.ArrayList;
import java.util.List;

import game.model.card.Card;
import game.model.card.Character;
import game.model.card.Position;

public final class Slot {

	private SlotType slotType;
	private Character character;
	private List<Card> markers = new ArrayList<>();
	private Position position = null;
	
	public Slot(SlotType slotType) {
		this.slotType = slotType;
	}
	
	public void stand() {
		//if(character != null)
			position = Position.STANDING;
	}

	public void rest() {
		//if(character != null)
			position = Position.RESTED;
	}

	public void reverse() {
		//if(character != null)
			position = Position.REVERSED;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public String toString() {
		String m = "Slot [slotType=" + slotType;
		if (character != null) {
			m +=  ", Position=" + position + ", character=" + character.toShortString()
				+  ", markers=" + markers.size();
		}

		return m + "]";
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
		stand();
	}
	
	public Character removeCharacter(){
		Character c = character;
		character = null;
		position = null;
		return c;
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

	public void setPosition(Position position) {
		this.position = position;
	}

}
