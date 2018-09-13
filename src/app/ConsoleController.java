package app;

import java.util.List;

import controller.GameManager;
import controller.PlayerController;
import io.DeckBuilder;
import io.RandomReader;
import io.ConsoleReadUserInput;
import model.board.Slot;
import model.board.Stage;
import model.card.Card;
import model.exceptions.InvalidDeckException;
import model.exceptions.InvalidIDException;

public class ConsoleController extends PlayerController {

	public ConsoleController(String name) {
		super(name, new ConsoleReadUserInput());
	}

	public static void main(String[] args) {
		PlayerController c1 = new ConsoleController("P1");
		PlayerController c2 = new ConsoleController("P2");
		c2.setReader(new RandomReader());
		GameManager gm = new GameManager(c1, c2);
		c1.readDeck();
		c2.readDeck();
		gm.gameLoop();
	}

	@Override
	public void buildDeck() {

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
		System.out.println(getPlayer().getName() + ": " + text.toString());
	}

	@Override
	public void displayStage() {
		System.out.println(getPlayer().getName() + " stage:");
		Stage stage = getBoard().getStage();
		for (Slot slot : stage.getSlots()) {
			System.out.println(slot);
		}
	}

	@Override
	public void displayHand() {
		System.out.println(getBoard().getHand());
	}

	@Override
	public void displayWaitingRoom() {
		System.out.println(getBoard().getWaitingRoom());
	}

	@Override
	public void displayDamageZone() {
		System.out.println(getBoard().getDamageZone());
	}
	

	@Override
	public void displayLevel() {
		System.out.println(getBoard().getLevel());
	}

	@Override
	public void displayStock() {
		System.out.println(getBoard().getStock());
	}

}
