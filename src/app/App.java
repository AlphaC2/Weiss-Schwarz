package app;


import controller.GameManager;
import controller.PlayerController;
import io.RandomReader;
import io.ConsoleReader;
import io.ConsoleWriter;

public class App {

	public static void main(String[] args) {
		ConsoleReader reader = new ConsoleReader();
		ConsoleWriter writer = new ConsoleWriter();
		PlayerController c1 = new PlayerController("P1", reader, writer);
		reader.setPC(c1);
		writer.setPC(c1);
		
		reader = new RandomReader();
		writer = new ConsoleWriter();
		PlayerController c2 = new PlayerController("P2", reader, writer);
		reader.setPC(c2);
		writer.setPC(c2);
		
		GameManager gm = new GameManager(c1, c2);
		c1.readDeck();
		c2.readDeck();
		gm.gameLoop();
	}
}
