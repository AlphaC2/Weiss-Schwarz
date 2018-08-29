package controller;

import java.util.List;

import app.Directories;
import io.DeckBuilder;
import io.ConsoleReadUserInput;
import model.card.Card;
import model.exceptions.InvalidDeckException;
import model.exceptions.InvalidIDException;

public class ConsoleController extends PlayerController {
	
	public ConsoleController(String name) {
		super(name, new ConsoleReadUserInput());
		
	}

	public static void main(String[] args) {
		
	}

	@Override
	public void buildDeck() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readDeck() {
		DeckBuilder db = new DeckBuilder();
		try {
			List<String> deckNames = db.getDecks();
			String deckname = getChoice("Select your deck", deckNames);
			List<Card> deck = db.readDeck("Decks/" + deckname + ".xml");
			log(deck.size() + " cards in deck");
			log("Finished Building Deck");
			setDeck(deck);
		} catch (InvalidIDException e) {
			e.printStackTrace();
		} catch (InvalidDeckException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void log(Object text) {
		System.out.println(text.toString());
	}

}
