package io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import model.card.Card;
import model.card.CardType;
import model.card.Character;
import model.card.Climax;
import model.card.Colour;
import model.card.Event;
import model.card.Rarity;
import model.card.Trigger;
import model.ability.action.Action;

public class CardXMLReader {

	public CardXMLReader() {
		super();
	}

	public Card read(String filePath) {
		//System.out.println(filePath);
		Card c = null;
		try {
			File fXmlFile = new File(filePath);
			//System.out.println(fXmlFile.exists());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			CardType type = CardType.parse(doc.getElementsByTagName("Type").item(0).getTextContent());
			String cardID = doc.getElementsByTagName("ID").item(0).getTextContent();
			String name = doc.getElementsByTagName("Name").item(0).getTextContent();
			Colour colour = Colour.parseString(doc.getElementsByTagName("Colour").item(0).getTextContent());
			int level = Integer.parseInt(doc.getElementsByTagName("Level").item(0).getTextContent());
			int cost = Integer.parseInt(doc.getElementsByTagName("Cost").item(0).getTextContent());

			List<Trigger> triggers = new ArrayList<Trigger>();
			for (int i = 0; i < doc.getElementsByTagName("Trigger").getLength(); i++) {
				triggers.add(Trigger.parseString(doc.getElementsByTagName("Trigger").item(i).getTextContent()));	
			}
			//triggers.add(Trigger.parseString(doc.getElementsByTagName("Trigger").item(1).getTextContent()));
			
			Rarity rarity= Rarity.parseString(doc.getElementsByTagName("Rarity").item(0).getTextContent());
			switch (type){
				case CHARACTER:
					int power = Integer.parseInt(doc.getElementsByTagName("Power").item(0).getTextContent());
					String trait1 = doc.getElementsByTagName("Trait").item(0).getTextContent();
					String trait2 = doc.getElementsByTagName("Trait").item(1).getTextContent();
					int soul = Integer.parseInt(doc.getElementsByTagName("Soul").item(0).getTextContent());
					List<Action> abilities = new ArrayList<Action>();
					
					c = new Character(name, cardID, "", level, cost, colour, triggers, rarity, "", trait1, trait2, power, soul, abilities);
					
					break;
				case CLIMAX:
					Action cAbility = null;
					c = new Climax(name, cardID, "", colour, triggers, rarity, "", cAbility);
					break;
				case EVENT:
					Action eAbility = null;
					c = new Event(name, cardID, "", level, cost, colour, triggers, rarity, "", eAbility);
					
					break;
				default: c=null; break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return c;
	}

}
