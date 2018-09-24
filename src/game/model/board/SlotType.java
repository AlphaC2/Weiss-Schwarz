package game.model.board;

public enum SlotType {
	FRONT_LEFT, FRONT_CENTER, FRONT_RIGHT, REAR_LEFT, REAR_RIGHT;
	
	public static boolean isFront(SlotType s){
		switch (s){
		case FRONT_LEFT: 	
		case FRONT_CENTER: 	
		case FRONT_RIGHT:	return true;
		default: return false;
		}
		
	}
	
	public static SlotType getAcross(SlotType s){
		switch(s){
		case FRONT_LEFT: 	return FRONT_RIGHT;
		case FRONT_CENTER: 	return FRONT_CENTER;
		case FRONT_RIGHT:	return FRONT_LEFT;
		default: throw new IllegalArgumentException("Invalid Slot:" + s);
		}
		
	}
}
