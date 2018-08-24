package model.card;

public enum CardType {
	CHARACTER, EVENT, CLIMAX;
	
	public static CardType parseJP(String text){
		switch (text){
		case "クライマックス":
			return CLIMAX;
		case "キャラ":
			return CHARACTER;
		case "イベント":
			return EVENT;
			default:
				throw new IllegalArgumentException(text);
		}
	}
}
