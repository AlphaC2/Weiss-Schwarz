package model.card;

import model.exceptions.ParseJPException;

public enum CardType {
	CHARACTER, EVENT, CLIMAX;
	
	public static CardType parse(String text){
		switch (text.toUpperCase()){
		case "クライマックス":
		case "CLIMAX":
			return CLIMAX;
		case "キャラ":
		case "CHARACTER":
			return CHARACTER;
		case "イベント":
		case "EVENT":
			return EVENT;
			default:
				throw new ParseJPException(text);
		}
	}
}
