package game.model.card;

import game.io.CardXMLReader;

public class DummyFactory {
	private static String path = "CardData\\DummySet\\";
	
	public static Card createCard(DummyName name){
		return CardXMLReader.read(path + name + ".xml");
	}
}
