package model.card;

public enum Colour {
	RED, YELLOW, BLUE, GREEN;

	public static Colour parseString(String s) {
		String sanitized = s.toLowerCase().trim();
		switch (sanitized) {
		case "赤色":
		case "red":
			return RED;
		case "黄色":
		case "yellow":
			return YELLOW;
		case "青色":
		case "blue":
			return BLUE;
		case "緑色":
		case "green":
			return GREEN;
		default:
			throw new ParseJPException(sanitized);

		}
	}
}
