package server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import game.controller.GameManager;
import game.controller.PlayerController;
import game.io.BetterRandomReader;
import game.io.ConsoleReader;
import game.io.ConsoleWriter;
import game.io.RandomReader;
import game.io.Reader;
import game.io.WebReader;
import game.io.Writer;

@SpringBootApplication
public class Application {
	
	private static void createGame(){
		Reader reader = new WebReader();
		Writer writer = new ConsoleWriter();
		PlayerController c1 = new PlayerController("P1", reader, writer);
		reader.setPC(c1);
		writer.setPC(c1);
		
//		reader = new ConsoleReader();
		reader = new BetterRandomReader();
		writer = new ConsoleWriter();
		PlayerController c2 = new PlayerController("P2", reader, writer);
		reader.setPC(c2);
		writer.setPC(c2);
		
		GameManager gm = new GameManager(c1, c2);
		c1.readDeck();
		c2.readDeck();
		gm.gameLoop();
	}

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        createGame();
        
    }
}