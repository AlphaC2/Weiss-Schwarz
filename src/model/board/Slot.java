package model.board;

public enum Slot {
	FRONT_LEFT, FRONT_CENTER, FRONT_RIGHT, REAR_LEFT, REAR_RIGHT;
	
	public static Slot getName(int i){
		switch(i){
		case 0: return FRONT_LEFT;
		case 1: return FRONT_CENTER;
		case 2:	return FRONT_RIGHT;
		case 3: return REAR_LEFT;
		case 4: return REAR_RIGHT;
		default: throw new IllegalArgumentException("Invalid Slot:" + i);
		}
	}
}
