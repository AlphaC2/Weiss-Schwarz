package game.model.card;

import game.model.exceptions.ParseJPException;

public enum Colour {
	RED, YELLOW, BLUE, GREEN, PURPLE;

	public static Colour parseString(String s) {
		String sanitized = s.toLowerCase().trim();
		switch (sanitized) {
		case "赤色":
		case "赤":
		case "red":
			return RED;
		case "黄色":
		case "黄":
		case "yellow":
			return YELLOW;
		case "青色":
		case "青":
		case "blue":
			return BLUE;
		case "緑色":
		case "緑":
		case "green":
			return GREEN;
		case "紫":
		case "紫色":
		case "purple":
			return PURPLE;
		default:
			throw new ParseJPException("Missing color " + sanitized);

		}
	}
}
