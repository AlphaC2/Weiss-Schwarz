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
	
	public static int getIndex(Slot s){
		switch(s){
		case FRONT_LEFT: 	return 0;
		case FRONT_CENTER: 	return 1;
		case FRONT_RIGHT:	return 2;
		case REAR_LEFT: 	return 3;
		case REAR_RIGHT: 	return 4;
		default: throw new IllegalArgumentException("Invalid Slot:" + s);
		}
	}
	
	public static Slot getAcross(Slot s){
		switch(s){
		case FRONT_LEFT: 	return FRONT_RIGHT;
		case FRONT_CENTER: 	return FRONT_CENTER;
		case FRONT_RIGHT:	return FRONT_LEFT;
		default: throw new IllegalArgumentException("Invalid Slot:" + s);
		}
		
	}
}
