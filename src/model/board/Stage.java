package model.board;

import java.util.ArrayList;
import java.util.List;

import model.card.Character;
import model.card.Position;

public class Stage {
	private Character[] stage;

	Stage() {
		super();
		stage = new Character[5];
	}
	/* Front_Left	Front_Center	Front_Right
	 * 		Rear_Left		Rear_Right
	 * */
	
	Character getSlot(Slot slot){
		int i = Slot.getIndex(slot);
		return stage[i];
	}
	
	void place(Character c, Slot slot){
		c.flipFaceUp();
		c.stand();
		int i = Slot.getIndex(slot);
		stage[i] = c;
	}
	
	void displayStage(){
		for (int i = 0; i < stage.length; i++) {
			if (stage[i] == null)
				System.out.println(i + " - " + Slot.getName(i) + ":");
			else
				System.out.println(i + " - " + Slot.getName(i) + ":" + stage[i].toString());
		}
	}

	void standAll() {
		if (stage[0] != null)
			stage[0].stand();
		if (stage[1] != null)
			stage[1].stand();
		if (stage[2] != null)
			stage[2].stand();
		if (stage[3] != null)
			stage[3].stand();
		if (stage[4] != null)
			stage[4].stand();
	}
	
	Character remove(Slot s){
		int index = Slot.getIndex(s);
		Character c = stage[index];
		stage[index] = null;
		return c;
		
	}
	
	int cardsOnStage(){
		int total = 0;
		for (int i = 0; i < stage.length; i++) {
			if (stage[i] != null) total++;
		}
		return total;
	}
	
	boolean hasChar(Slot s){
		return stage[Slot.getIndex(s)] != null;
	}
	
	boolean rest(Slot s){
		int index = Slot.getIndex(s);
		if (stage[index] == null){
			System.out.println("No Character in slot");
			return false;
		}else if(stage[index].getState() != Position.STANDING){
			System.out.println("Character not Standing:Unable to attack");
			return false;
		}else{
			stage[index].rest();
			return true;
		}
	}

	public Character getCard(Slot s){
		int index = Slot.getIndex(s);
		if (stage[index] != null){
			return stage[index];
		}
		return null;
	}
	
	public Slot getSlot(Character c){
		for (int i = 0; i < stage.length; i++) {
			if (stage[i] != null && stage[i].equals(c))
				return Slot.getName(i);
		}
		System.out.println("Character not on Stage");
		return null;
	}

	public List<Character> getReversed() {
		List<Character> result = new ArrayList<Character>();
		for (int i = 0; i < stage.length; i++) {
			if (stage[i] != null && stage[i].getState() == Position.REVERSED)
				result.add(stage[i]);
		}
		return result;
	}
	
}
