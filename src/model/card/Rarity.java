package model.card;

public enum Rarity {
	TD, C, U, R, RR, RRR, SR;
	
	public static Rarity parseString(String s){
		String sanitized = s.toLowerCase().trim();
		switch (sanitized){
			case "td": return TD;
			case "c": return C;
			case "u": return U;
			case "r": return R;
			case "rr": return RR;
			case "rrr": return RRR;
			case "sr": return SR;
			default: return null;
				
		}
	}
}
