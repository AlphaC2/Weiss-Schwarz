package app;


import controller.ConsoleController;
import controller.GameManager;
import controller.PlayerController;
import io.CardXMLReader;
import model.card.Card;


public class MainApp {

	public static void main(String[] args) {
		PlayerController c1 = new ConsoleController("P1");
		PlayerController c2 = new ConsoleController("P2");
		GameManager gm = new GameManager(c1, c2);
		gm.gameLoop();
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
