package game.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import game.model.ability.Ability;
import game.model.card.Card;
import game.model.card.CardType;
import game.model.card.Character;
import game.model.card.Climax;
import game.model.card.Colour;
import game.model.card.Event;
import game.model.card.Rarity;
import game.model.card.Trigger;
import game.model.deck.CardSet;

public class CardXMLReader {
	
	private final static String path = "CardData/";

	public CardXMLReader() {
		super();
	}
	
	public static Set<Card> readSet(String setID){
		File f = new File(path + setID.replace("-", "/"));
		Set<Card> cardSet = new HashSet<>();
		if (f.exists() && f.isDirectory()){
			for (File card : f.listFiles()) {
				Card c = read(card.getAbsolutePath());
				cardSet.add(c);
			}
		}
		return cardSet;
	}
	
	private static String readString(Document doc, String tag){
		String s;
		try {
			s = doc.getElementsByTagName(tag).item(0).getTextContent();
		} catch (NullPointerException e){
			s = "";
		}
		return s;
	}

	public static Card read(String filePath) {
		// System.out.println(filePath);
		Card c = null;
		try {
			File fXmlFile = new File(filePath);
			// System.out.println(fXmlFile.exists());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			CardType type = CardType.parse(doc.getElementsByTagName("Type").item(0).getTextContent());
			String cardID = doc.getElementsByTagName("ID").item(0).getTextContent();
			String name = doc.getElementsByTagName("Name").item(0).getTextContent();
			String imageUrl = readString(doc,"ImageURL");
			String flavourText = readString(doc,"FlavourText");
			Colour colour = Colour.parseString(doc.getElementsByTagName("Colour").item(0).getTextContent());
			int level = Integer.parseInt(doc.getElementsByTagName("Level").item(0).getTextContent());
			int cost = Integer.parseInt(doc.getElementsByTagName("Cost").item(0).getTextContent());

			List<Trigger> triggers = new ArrayList<Trigger>();
			for (int i = 0; i < doc.getElementsByTagName("Trigger").getLength(); i++) {
				triggers.add(Trigger.parseString(doc.getElementsByTagName("Trigger").item(i).getTextContent()));
			}
			// triggers.add(Trigger.parseString(doc.getElementsByTagName("Trigger").item(1).getTextContent()));

			Rarity rarity = Rarity.parseString(doc.getElementsByTagName("Rarity").item(0).getTextContent());
			switch (type) {
			case CHARACTER:
				int power = Integer.parseInt(doc.getElementsByTagName("Power").item(0).getTextContent());
				NodeList nodelist = doc.getElementsByTagName("Trait");
				String trait1 = doc.getElementsByTagName("Trait").item(0).getTextContent().toUpperCase();
				String trait2 = nodelist.getLength() > 1 ? nodelist.item(1).getTextContent().toUpperCase() : "NONE";
				int soul = Integer.parseInt(doc.getElementsByTagName("Soul").item(0).getTextContent());
				List<Ability> abilities = new ArrayList<Ability>();

				c = new Character(name, cardID, imageUrl, level, cost, colour, triggers, rarity, flavourText, trait1,
						trait2, power, soul);

				break;
			case CLIMAX:
				Ability cAbility = null;
				c = new Climax(name, cardID, imageUrl, colour, triggers, rarity, flavourText, cAbility);
				break;
			case EVENT:
				Ability eAbility = null;
				c = new Event(name, cardID, imageUrl, level, cost, colour, triggers, rarity, flavourText, eAbility);

				break;
			default:
				c = null;
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return c;
	}

}
