package game.controller;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.io.BetterRandomReader;
import game.io.ConsoleWriter;
import game.io.Reader;
import game.io.WebReader;
import game.io.Writer;

public class GameManagerPool {
	private static Map<Integer, GameManager> games = new ConcurrentHashMap<>();
	private static final int MAX_GAMES = 20;
	
	public static GameManager getGameManager(int id){
		GameManager gm = games.get(id);
		return gm;
	}
	
	public static int createGameManager(){
		Reader p1r = new WebReader();
		Writer p1w = new ConsoleWriter();
		
		Reader p2r = new BetterRandomReader();
		Writer p2w = new ConsoleWriter();
		
		return createGameManager(p1r, p1w, p2r, p2w);
	}
	
	public static int createGameManager(Reader p1Reader, Writer p1Writer, Reader p2Reader, Writer p2Writer){
		if(games.size() >= MAX_GAMES){
			return -1;
		}
		
		PlayerController c1 = new PlayerController("P1", p1Reader, p1Writer);
		p1Reader.setPC(c1);
		p1Writer.setPC(c1);
		
		PlayerController c2 = new PlayerController("P2", p2Reader, p2Writer);
		p2Reader.setPC(c2);
		p2Writer.setPC(c2);
		
		Random r = new Random();
		int id = Math.abs(r.nextInt());
		while(games.keySet().contains(id)){
			id = Math.abs(r.nextInt());
		}
		
		GameManager gm = new GameManager(c1, c2);
		games.put(id, gm);
		
		Thread thread = gm.getThread();
		thread.start();
		
		System.out.println(games.size() + " games Currently Running");
		System.out.println("IDs:");
		for (Integer printId : games.keySet()) {
			System.out.println(printId);
		}
		
//		System.out.println("FINISHED MAKING GM");
		return id;
	}
	
	public static boolean endGame(int id){
		GameManager gm = games.get(id);
		if(gm == null){
			return false;
		}
		
		games.remove(id);
		gm.getThread().interrupt();
		
		System.out.println(games.size() + " games Currently Running");
		System.out.println("IDs:");
		for (Integer tempId : games.keySet()) {
			System.out.println(tempId);
		}
		
		return true;
	}
	
	public static Set<Integer> getGameIDs(){
		return games.keySet();
	}
}