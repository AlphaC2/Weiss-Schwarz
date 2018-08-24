package model.card;

public enum Trait {
	ALIEN, ANGEL, ANIMAL, BANANA, BLACKSMITH, BLOOD, BOOK, BRIGADE_CHIEF, BUG, CAMERA, CHAIRMAN, CLONE, DEAD, DELINQUENT, DETECTIVE, DIVINE, DIVINE_TREASURE, DOLL, DOUBLE_TOOTH, DRAGON, DRAMA, ESPER, FAMILIAR, FLAME, FEMALE_OUTFIT, FROG, FOREHEAD, GAME, GLASSES, HEADPHONES, HOMONCULUS, ICE, INFIRMARY, ILLNESS, JEWEL, JUDGEMENT, JUNES, KEY, LOVE, MAGIC, MAID, MANAGER, MANGA, MASK, MASTER, MECHA, MELON_BREAD, MILK, MUSCLE, MUSIC, NARCISSIST, NINJA, NOVEL, OBENTO, ODD_EYE, ODDITY, ONLINE_GAME, OTAKU, PAJAMAS, PILOT, POLICE, ROYALTY, SAKE, SCIENCE, SERVANT, SHADOW, SHIELD, SPIRIT, SPORTS, SWEETS, SWIMSUIT, STATIONERY, STUDENT_COUNCIL, TEA, TEACHER, TELEVISION, THIEF, TIME, TWINS, UNISON, WAFUKU, WAITRESS, WATERMELON, WEAPON, VOID, NONE;

	public static Trait parseString(String s) {
		String sanitized = s.toLowerCase().trim();
		switch (sanitized) {
		case "alien":
			return ALIEN;
		case "angel":
			return ANGEL;
		case "animal":
			return ANIMAL;
		case "banana":
			return BANANA;
		case "blacksmith":
			return BLACKSMITH;
		case "blood":
			return BLOOD;
		case "book":
			return BOOK;
		case "brigade chief":
			return BRIGADE_CHIEF;
		case "bug":
			return BUG;
		case "camera":
			return CAMERA;
		case "chairman":
			return CHAIRMAN;
		case "clone":
			return CLONE;
		case "dead":
			return DEAD;
		case "delinquent":
			return DELINQUENT;
		case "detective":
			return DETECTIVE;
		case "divine":
			return DIVINE;
		case "divine treasure":
			return DIVINE_TREASURE;
		case "doll":
			return DOLL;
		case "double tooth":
			return DOUBLE_TOOTH;
		case "dragon":
			return DRAGON;
		case "drama":
			return DRAMA;
		case "esper":
			return ESPER;
		case "familiar":
			return FAMILIAR;
		case "flame":
			return FLAME;
		case "female outfit":
			return FEMALE_OUTFIT;
		case "frog":
			return FROG;
		case "forehead":
			return FOREHEAD;
		case "ゲーム":
		case "game":
			return GAME;
		case "glasses":
			return GLASSES;
		case "headphones":
			return HEADPHONES;
		case "homonculus":
			return HOMONCULUS;
		case "ice":
			return ICE;
		case "infirmary":
			return INFIRMARY;
		case "illness":
			return ILLNESS;
		case "jewel":
			return JEWEL;
		case "judgement":
			return JUDGEMENT;
		case "junes":
			return JUNES;
		case "key":
			return KEY;
		case "love":
			return LOVE;
		case "magic":
			return MAGIC;
		case "maid":
			return MAID;
		case "manager":
			return MANAGER;
		case "漫画":
		case "manga":
			return MANGA;
		case "mask":
			return MASK;
		case "master":
			return MASTER;
		case "mecha":
			return MECHA;
		case "melon bread":
			return MELON_BREAD;
		case "milk":
			return MILK;
		case "muscle":
			return MUSCLE;
		case "音楽":
		case "music":
			return MUSIC;
		case "narcissist":
			return NARCISSIST;
		case "ninja":
			return NINJA;
		case "小説":
		case "novel":
			return NOVEL;
		case "obento":
			return OBENTO;
		case "odd eye":
			return ODD_EYE;
		case "oddity":
			return ODDITY;
		case "online game":
			return ONLINE_GAME;
		case "オタク":
		case "otaku":
			return OTAKU;
		case "pajamas":
			return PAJAMAS;
		case "pilot":
			return PILOT;
		case "police":
			return POLICE;
		case "royalty":
			return ROYALTY;
		case "sake":
			return SAKE;
		case "science":
			return SCIENCE;
		case "servant":
			return SERVANT;
		case "shadow":
			return SHADOW;
		case "shield":
			return SHIELD;
		case "spirit":
			return SPIRIT;
		case "sports":
			return SPORTS;
		case "sweets":
			return SWEETS;
		case "swimsuit":
			return SWIMSUIT;
		case "stationery":
			return STATIONERY;
		case "student council":
			return STUDENT_COUNCIL;
		case "tea":
			return TEA;
		case "teacher":
			return TEACHER;
		case "television":
			return TELEVISION;
		case "thief":
			return THIEF;
		case "time":
			return TIME;
		case "twins":
			return TWINS;
		case "unison":
			return UNISON;
		case "wafuku":
			return WAFUKU;
		case "waitress":
			return WAITRESS;
		case "watermelon":
			return WATERMELON;
		case "weapon":
			return WEAPON;
		case "void":
			return VOID;
		case "none":
			return NONE;
		default:
			throw new ParseJPException(sanitized);

		}
	}
}
