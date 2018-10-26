package game.controller;

import java.time.Duration;
import java.time.LocalDateTime;

public class GameManagerGarbageCollector implements Runnable{
	private static GameManagerGarbageCollector gmgc;
	private static final long TIMEOUT = 11; //seconds
	private static final long UPDATE = 5000; //milliseconds
	
	private GameManagerGarbageCollector() {
		super();
	}
	
	public static void start(){
		if (gmgc == null){
			gmgc = new GameManagerGarbageCollector();
			Thread thread = new Thread(gmgc);
			thread.start();
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(UPDATE);
				for (Integer id: GameManagerPool.getGameIDs()) {
					GameManager gm = GameManagerPool.getGameManager(id);
					if ((Duration.between(gm.getLastAction(), LocalDateTime.now()).getSeconds() > TIMEOUT) ||
							!gm.isAlive()){
						GameManagerPool.endGame(id);
						System.out.println("Garbage Collected game " + id);
					}
				}
			} catch (InterruptedException e) {
				return;
			}
			
		}
	}
	
}
