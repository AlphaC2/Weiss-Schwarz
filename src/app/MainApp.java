package app;

import java.util.List;

import controller.GameManager;
import io.CardXMLReader;
import io.DeckBuilder;
import model.card.Card;
import model.exceptions.InvalidDeckException;
import model.exceptions.InvalidIDException;
import model.player.Player;

public class MainApp {

	public static void main(String[] args) {
		//readCard();
		List<Card> deck = buildDeck();
		Player p1 = new Player(deck, "P1");
		Player p2 = new Player(deck, "P2");
		GameManager gm = new GameManager(p1, p2);
		gm.gameLoop();
		System.out.println("Game Ended");
		
	}
	public static List<Card> buildDeck(){
		DeckBuilder db = new DeckBuilder();
		try {
			List<Card> deck = 	db.readDeck(Directories.homeDirectory+"\\Decks\\AngelBeats1.xml");
			System.out.println(deck.size() + " cards in deck");
			for (Card card : deck) {
				//System.out.println(card);
			}
			System.out.println("Finished Building Deck");
			return deck;	
		} catch (InvalidIDException e) {
			e.printStackTrace();
		} catch (InvalidDeckException e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	
	public static void readCard(){
		CardXMLReader reader = new CardXMLReader();
		Card c = reader.read(Directories.homeDirectory+"\\CardData\\AB\\W11\\AB-W11-101.xml");
		Card cc = reader.read(Directories.homeDirectory+"\\CardData\\AB\\W11\\AB-W11-T07.xml");
		System.out.println(c);
		System.out.println(cc);
		System.out.println("Finished Making Cards");
	}

}
