package model.board;

import model.card.Character;

public class Stage {
	Character[] stage;

	public Stage() {
		super();
		stage = new Character[5];
	}
	/* Front_Left	Front_Center	Front_Right
	 * 		Rear_Left		Rear_Right
	 * */
	
	public Character getSlot(Slot slot){
		switch(slot){
			case FRONT_LEFT: return stage[0];
			case FRONT_CENTER: return stage[1];
			case FRONT_RIGHT: return stage[2];
			case REAR_LEFT: return stage[3];
			case REAR_RIGHT: return stage[4];
			default: return null;
		}
	}
	
	public void place(Character c, Slot slot){
		switch(slot){
			case FRONT_LEFT: stage[0] = c;
			case FRONT_CENTER: stage[1] = c;
			case FRONT_RIGHT: stage[2] = c;
			case REAR_LEFT: stage[3] = c;
			case REAR_RIGHT: stage[4] = c;
		}
	}
	
	public void displayStage(){
		if (stage[0] != null)
			System.out.println("Front Left:" + stage[0]);
		if (stage[1] != null)
			System.out.println("Front Center:" + stage[1]);
		if (stage[2] != null)
			System.out.println("Front Right:" + stage[2]);
		if (stage[3] != null)
			System.out.println("Rear Left:" + stage[3]);
		if (stage[4] != null)
			System.out.println("Rear Right:" + stage[4]);
	}

	public void standAll() {
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
	
	
}
