package io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import app.Directories;
import model.card.Card;
import model.card.Climax;
import model.exceptions.InvalidDeckException;
import model.exceptions.InvalidIDException;

public class DeckBuilder {
	
	public List<Card> readDeck(String DeckFilePath) throws InvalidIDException, InvalidDeckException{
		//System.out.println(DeckFilePath);
		List<Card> deck = new ArrayList<Card>(50);
		CardXMLReader reader = new CardXMLReader();
		try {
			File fXmlFile = new File(DeckFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			NodeList nodelist = doc.getElementsByTagName("Card");
			for (int i = 0; i < nodelist.getLength(); i++) {
				Element element = (Element) nodelist.item(i);
				String id = element.getElementsByTagName("ID").item(0).getTextContent();
				String[] sections = id.split("-");
				if (sections.length != 3)
					throw new InvalidIDException();
				int quantity = Integer.parseInt(element.getElementsByTagName("Quantity").item(0).getTextContent());
				String filepath = "CardData\\"+ sections[0] + "\\" + sections[1] + "\\" + id+".xml";
				
				for (int j = 0; j < quantity; j++) {
					Card c = reader.read(filepath);
					deck.add(c);
				}
				
				//System.out.println(filepath);
				
				
				
			}
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(verify(deck))
			return deck;
		else
			throw new InvalidDeckException();
	}
	
	public boolean verify(List<Card> deck){
		int climax = 0;
		
		for (Card card : deck) {
			//System.out.println(card);
			if(card instanceof Climax)
				climax++;
		}
		
		return climax == 8;
	}

	public List<String> getDecks() {
		File f = new File("Decks");
		List<String> outputs = new ArrayList<>();
		FilenameFilter xmlFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".xml")) {
					return true;
				} else {
					return false;
				}
			}
		};
		for (File file : f.listFiles(xmlFilter)) {
			outputs.add(file.getName().replace(".xml", ""));
		}
		return outputs;
	}
	
}
