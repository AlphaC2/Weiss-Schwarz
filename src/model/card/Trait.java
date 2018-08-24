package model.card;

import model.exceptions.ParseJPException;

public enum Trait {
	ALIEN, ANGEL, ANIMAL, 
	BANANA, BLACKSMITH, BLOOD, BOOK, BRIGADE_CHIEF, BUG, BUTLER,
	CAMERA, CHAIRMAN, CLONE, 
	DEAD, DELINQUENT, DETECTIVE, DIVINE, DIVINE_TREASURE, DOLL, DOUBLE_TOOTH, DRAGON, DRAMA, DRESS,ESPER, 
	FACTORY, FAMILIAR, FLAME, FEMALE_OUTFIT, FROG, FOREHEAD, 
	GAME, GLASSES, GOD,
	HEADPHONES, HOMONCULUS, 
	ICE, INFIRMARY, ILLNESS, 
	JEWEL, JUDGEMENT, JUNES, 
	KEY, 
	LOVE, 
	MAGIC, MAID, MANAGER, MANGA, MASK, MASTER, MECHA, MELON_BREAD, MILK, MUSCLE, MUSIC, 
	NARCISSIST, NET, NINJA, NOVEL, 
	OBENTO, OCCULT, ODD_EYE, ODDITY, ONLINE_GAME, OTAKU, 
	PAJAMAS, PILOT, PLANT, POLICE, 
	ROYALTY, 
	SAKE, SCIENCE, SERVANT, SHADOW, SHIELD, SPIRIT, SPORTS, SWEETS, SWIMSUIT, STATIONERY, STUDENT_COUNCIL, 
	TEA, TEACHER, TELEVISION, THIEF, TIME, TWINS, 
	UNISON, 
	WAFUKU, WAITRESS, WATERMELON, WEAPON, 
	VOID, 
	NONE;

	public static Trait parseString(String s) {
		String sanitized = s.toLowerCase().trim();
		switch (sanitized) {
		case "alien":
			return ALIEN;
		case "angel":
			return ANGEL;
		case "動物":
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
		case "執事":
		case "butler":
			return BUTLER;
		case "camera":
			return CAMERA;
		case "委員長":
		case "chairman":
			return CHAIRMAN;
		case "clone":
			return CLONE;
		case "dead":
			return DEAD;
		case "不良":
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
		case "ドレス": 
		case "dress":
			return DRESS;
		case "超能力":
		case "esper":
			return ESPER;
		case "工場":
		case "factory":
			return FACTORY;
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
		case "メガネ":
		case "glasses":
			return GLASSES;
		case "神":
		case "god":
			return GOD;
		case "ヘッドフォン":
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
		case "風紀委員":
		case "judgement":
			return JUDGEMENT;
		case "junes":
			return JUNES;
		case "key":
			return KEY;
		case "愛":
		case "love":
			return LOVE;
		case "魔法":
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
		case "メカ":
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
		case "ネット":
		case "net":
			return NET;
		case "ninja":
			return NINJA;
		case "小説":
		case "novel":
			return NOVEL;
		case "obento":
			return OBENTO;
		case "オカルト":
		case "occult":
			return OCCULT;
		case "odd eye":
			return ODD_EYE;
		case "oddity":
			return ODDITY;
		case "ネトゲ":
		case "online game":
			return ONLINE_GAME;
		case "オタク":
		case "otaku":
			return OTAKU;
		case "パジャマ":
		case "pajamas":
			return PAJAMAS;
		case "pilot":
			return PILOT;
		case "植物":
		case "plant":
			return PLANT;
		case "警察":
		case "police":
			return POLICE;
		case "royalty":
			return ROYALTY;
		case "sake":
			return SAKE;
		case "科学":
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
		case "お菓子":
		case "sweets":
			return SWEETS;
		case "水着":
		case "swimsuit":
			return SWIMSUIT;
		case "stationery":
			return STATIONERY;
		case "生徒会":
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
		case "時間":
		case "time":
			return TIME;
		case "双子":
		case "twins":
			return TWINS;
		case "unison":
			return UNISON;
		case "和服":
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
