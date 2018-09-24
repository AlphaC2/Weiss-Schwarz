package game.model.card;

public enum Side {
	WEISS, SCHWARZ;
	
	public static Side parseString(String s){
		String sanitized = s.toLowerCase().trim();
		switch (sanitized){
			case "weiss": return WEISS;
			case "schwarz": return SCHWARZ;
			default: return null;
				
		}
	}
	
}
