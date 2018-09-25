package game.io;

import java.util.List;

import game.controller.PlayerController;
import game.model.card.Card;
import game.model.exceptions.InvalidDeckException;
import game.model.exceptions.InvalidIDException;

public abstract class Reader {
	protected PlayerController pc;
	
	public final void setPC(PlayerController pc){
		this.pc = pc;
	}
	
	public abstract <T> T getChoice(String prompt, List<T> choices);
	public abstract boolean getChoice(String prompt);
	public abstract void buildDeck();
	
	public List<Card> readDeck() {

		DeckBuilder db = new DeckBuilder();
		try {
			List<String> deckNames = db.getDecks();
			String prompt = "Select your deck";
			String deckname = getChoice(prompt, deckNames);
			return db.readDeck("Decks/" + deckname + ".xml");
		} catch (InvalidIDException e) {
			e.printStackTrace();
		} catch (InvalidDeckException e) {
			e.printStackTrace();
		}
		return null;

	}
}
