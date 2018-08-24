package model.card;

public enum Trigger {

	NONE, SOUL, TWOSOUL, WHIRLWIND, GOLDBAG, DOOR, BOOK, FIRE, GOLDBAR, PANTS, STANDBY;

	public static Trigger[] parseImage(String url) {
		System.out.println(url);
		switch (url.split("/ws/")[1]) {
		case "icon_tri_0.png":
			return new Trigger[] { NONE };
		case "icon_tri_1.png":
			return new Trigger[] { SOUL };
		case "icon_tri_2.png":
			return new Trigger[] {SOUL, SOUL};
		case "icon_tri_1A.png":
			return new Trigger[] { SOUL, WHIRLWIND };
		case "icon_tri_1E.png":
			return new Trigger[] {FIRE, SOUL};
		case "icon_tri_G.png":
		case "icon_tri_1G.png":
			return new Trigger[] { SOUL, PANTS};
		case "icon_tri_1H.png":
			return new Trigger[] {SOUL, STANDBY};
		case "icon_tri_B.png":
			return new Trigger[] {GOLDBAG};
		case "icon_tri_C.png":
			return new Trigger[] {DOOR};
		case "icon_tri_D.png":
			return new Trigger[] {BOOK};
		case "icon_tri_F.png":
			return new Trigger[] {GOLDBAR};

	
		

			
		default: throw new IllegalArgumentException(url);
		}
	}

	public static Trigger parseString(String s) {
		String sanitized = s.toLowerCase().trim();
		switch (sanitized) {
		case "none":
			return NONE;
		case "soul":
			return SOUL;
		case "twosoul":
			return TWOSOUL;
		case "whirlwind":
			return WHIRLWIND;
		case "goldbag":
			return GOLDBAG;
		case "door":
			return DOOR;
		case "book":
			return BOOK;
		case "fire":
			return FIRE;
		case "goldbar":
			return GOLDBAR;
		case "pants":
			return PANTS;
		case "standby":
			return STANDBY;
		default: throw new IllegalArgumentException();

		}
	}
}
