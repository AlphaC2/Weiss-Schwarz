package model.card;

public enum Trigger {
	NONE, SOUL, TWOSOUL, WHIRLWIND, GOLDBAG, DOOR, BOOK, FIRE, GOLDBAR, PANTS, STANDBY;
	
	public static Trigger parseString(String s){
		String sanitized = s.toLowerCase().trim();
		switch (sanitized){
			case "none": return NONE;
			case "soul": return SOUL;
			case "twosoul": return TWOSOUL;
			case "whirlwind": return WHIRLWIND;
			case "goldbag": return GOLDBAG;
			case "door": return DOOR;
			case "book": return BOOK;
			case "fire": return FIRE;
			case "goldbar": return GOLDBAR;
			case "pants": return PANTS;
			case "standby": return STANDBY;
			default: return null;
				
		}
	}
}
