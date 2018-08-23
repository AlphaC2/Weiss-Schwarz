package model.card;

public enum Colour {
	RED, YELLOW, BLUE, GREEN;
	
	public static Colour parseString(String s){
		String sanitized = s.toLowerCase().trim();
		switch (sanitized){
			case "red": return RED;
			case "yellow": return YELLOW;
			case "blue": return BLUE;
			case "green": return GREEN;
			default: return null; //TODO
				
		}
	}
}
