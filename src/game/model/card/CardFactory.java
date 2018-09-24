package game.model.card;

import game.io.CardXMLReader;

public class CardFactory {
	private static String path = "CardData\\";
	
	public static Card createCard(String id){
		String series = id.split("-")[0];
		String set = id.split("-")[1];
		return CardXMLReader.read(path + series + "\\" + set + "\\" + id + ".xml");
	}
}
