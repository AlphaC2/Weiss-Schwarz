package io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import model.card.Card;
import model.card.Character;
import model.card.Climax;
import model.card.Event;
import model.card.Rarity;
import model.card.Trait;
import model.card.Trigger;
import model.card.ability.Ability;

public class CardXMLReader {

	public CardXMLReader() {
		super();
	}

	public Card read(String filePath) {
		//System.out.println(filePath);
		Card c = null;
		try {
			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			String type = doc.getElementsByTagName("Type").item(0).getTextContent();
			String cardID = doc.getElementsByTagName("ID").item(0).getTextContent();
			String name = doc.getElementsByTagName("Name").item(0).getTextContent();
			String colour = doc.getElementsByTagName("Colour").item(0).getTextContent();
			int level = Integer.parseInt(doc.getElementsByTagName("Level").item(0).getTextContent());
			int cost = Integer.parseInt(doc.getElementsByTagName("Cost").item(0).getTextContent());

			List<Trigger> triggers = new ArrayList<Trigger>();
			for (int i = 0; i < doc.getElementsByTagName("Trigger").getLength(); i++) {
				triggers.add(Trigger.parseString(doc.getElementsByTagName("Trigger").item(i).getTextContent()));	
			}
			//triggers.add(Trigger.parseString(doc.getElementsByTagName("Trigger").item(1).getTextContent()));
			
			Rarity rarity= Rarity.parseString(doc.getElementsByTagName("Rarity").item(0).getTextContent());
			
			switch (type){
				case "Character":
					int power = Integer.parseInt(doc.getElementsByTagName("Power").item(0).getTextContent());
					Trait trait1 = Trait.parseString(doc.getElementsByTagName("Trait").item(0).getTextContent());
					Trait trait2 = Trait.parseString(doc.getElementsByTagName("Trait").item(1).getTextContent());
					int soul = Integer.parseInt(doc.getElementsByTagName("Soul").item(0).getTextContent());
					List<Ability> abilities = new ArrayList<Ability>();
					
					c = new Character(name, cardID, "", level, cost, colour, triggers, rarity, "", trait1, trait2, power, soul, abilities);
					
					break;
				case "Climax":
					Ability cAbility = null;
					c = new Climax(name, cardID, "", colour, triggers, rarity, "", cAbility);
					break;
				case "Event":
					Ability eAbility = null;
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
