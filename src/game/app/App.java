package game.app;

import game.controller.GameManagerGarbageCollector;
import game.controller.GameManagerPool;
import game.io.BetterRandomReader;
import game.io.ConsoleReader;
import game.io.ConsoleWriter;

public class App {

	public static void main(String[] args) {
		ConsoleReader p1r = new ConsoleReader();
		ConsoleWriter p1w = new ConsoleWriter();
		
		ConsoleReader p2r = new BetterRandomReader();
		ConsoleWriter p2w = new ConsoleWriter();

		int gameID = GameManagerPool.createGameManager(p1r, p1w, p2r, p2w);
		GameManagerPool.getGameManager(gameID);
		GameManagerGarbageCollector.start();
		System.out.println("APP GAME ID:" + gameID);
		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		gm.alive = false;
//		gm.getThread().interrupt();
	}
}
